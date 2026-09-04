import assert from 'node:assert/strict';

// Build-time lexical check for canonical function declarations, not a Kotlin compiler.
// Strings/comments are opaque so their punctuation cannot change parameter nesting.
function tokens(source) {
  return [...source.matchAll(/\/\*[\s\S]*?\*\/|\/\/[^\r\n]*|"""[\s\S]*?"""|"(?:\\.|[^"\\])*"|'(?:\\.|[^'\\])*'|->|[A-Za-z_$][\w$]*|[^\s]/g)]
    .map(match => match[0]).filter(token => !token.startsWith('//') && !token.startsWith('/*'));
}

export const normalizeKotlin = source => tokens(source).join('');

/** The first public overload is the canonical registry API; other overloads stay documented in KDoc. */
export function kotlinParameters(source, name) {
  const input = tokens(source);
  const start = input.findIndex((token, index) => token === 'fun' && input[index + 1] === name && input[index + 2] === '(');
  assert(start >= 0, `Missing Kotlin function: ${name}`);
  const parameters = [];
  let segment = [], stack = [];
  const append = () => {
    if (!segment.length) return;
    assert.equal(segment[1], ':', `Unsupported canonical parameter in ${name}: ${segment.join(' ')}`);
    const equals = segment.indexOf('=');
    parameters.push({ name: segment[0], type: segment.slice(2, equals < 0 ? undefined : equals).join(''),
      defaultValue: equals < 0 ? 'required' : segment.slice(equals + 1).join('') });
    segment = [];
  };
  for (const token of input.slice(start + 3)) {
    if (token === ')' && stack.length === 0) { append(); return parameters; }
    if (token === ',' && stack.length === 0) { append(); continue; }
    if (['(', '{', '[', '<'].includes(token)) stack.push(token);
    else if ([')', '}', ']', '>'].includes(token)) {
      const expected = { ')': '(', '}': '{', ']': '[', '>': '<' }[token];
      assert.equal(stack.pop(), expected, `Unbalanced canonical signature: ${name}`);
    }
    segment.push(token);
  }
  assert.fail(`Unterminated canonical signature: ${name}`);
}

export function verifyPropertyMetadata(source, component) {
  const actual = kotlinParameters(source, component.name);
  const documented = component.properties.map(({ name, type, defaultValue }) => ({ name,
    type: normalizeKotlin(type), defaultValue: normalizeKotlin(defaultValue) }));
  assert.deepEqual(documented, actual, `Registry API differs from Kotlin parameters: ${component.name}`);
}
