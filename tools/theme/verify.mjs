import fs from 'node:fs';
import path from 'node:path';
import { fileURLToPath } from 'node:url';

const root = path.resolve(path.dirname(fileURLToPath(import.meta.url)), '../..');
const read = relative => fs.readFileSync(path.join(root, relative), 'utf8');

/** Small drift check for the existing Kotlin defaults grammar; this is not a token compiler. */
export function verifyThemeColors(css = read('docs/src/index.css')) {
  const palette = read('frogui-foundation/src/main/java/io/github/codewitheswar/frogui/foundation/color/FrogPalette.kt');
  const defaults = read('frogui-theme/src/main/kotlin/io/github/codewitheswar/frogui/theme/FrogThemeDefaults.kt');
  const fields = [...read('frogui-foundation/src/main/java/io/github/codewitheswar/frogui/foundation/color/FrogColors.kt')
    .matchAll(/val (\w+): Color/g)].map(m => m[1]);
  const raw = new Map([...palette.matchAll(/val (\w+)(?:: Color)?\s*=\s*Color\(0x([0-9A-Fa-f]{8})\)/g)].map(m => [m[1], m[2]]));
  let count = 0;
  for (const [scheme, selector] of [['light', ':root'], ['dark', '.dark']]) {
    const block = css.slice(css.indexOf(`${selector} {`)).split('}')[0];
    const kotlin = defaults.split(`fun ${scheme}Colors(): FrogColors = FrogColors(`)[1]?.split('\n    )')[0];
    if (!kotlin || !block) throw new Error(`Missing ${scheme} color block`);
    for (const field of fields) {
      const expression = kotlin.match(new RegExp(`\\b${field}\\s*=\\s*(FrogPalette\\.\\w+|Color\\(0x[0-9A-Fa-f]{8}\\))`))?.[1];
      const argb = expression?.startsWith('FrogPalette.') ? raw.get(expression.split('.')[1]) : expression?.match(/0x([0-9A-Fa-f]{8})/)?.[1];
      if (!argb) throw new Error(`Unsupported or missing Kotlin color: ${scheme}.${field}`);
      const expected = `#${argb.slice(2)}${argb.slice(0, 2) === 'FF' ? '' : argb.slice(0, 2)}`.toLowerCase();
      const token = `--frog-${field.replace(/[A-Z]/g, c => `-${c.toLowerCase()}`)}`;
      const actual = block.match(new RegExp(`${token}:\\s*(#[0-9a-fA-F]+);`))?.[1].toLowerCase();
      if (actual !== expected) throw new Error(`${scheme} ${token}: expected ${expected}, found ${actual}`);
      count++;
    }
  }
  return count;
}

if (process.argv[1] && path.resolve(process.argv[1]) === fileURLToPath(import.meta.url)) {
  console.log(`Theme CSS matches ${verifyThemeColors()} canonical Kotlin color values.`);
}
