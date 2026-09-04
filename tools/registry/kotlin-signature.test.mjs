import test from 'node:test';
import assert from 'node:assert/strict';
import { kotlinParameters, verifyPropertyMetadata } from './kotlin-signature.mjs';

test('canonical signature handles scoped slots, generic types, lambdas, strings and trailing commas', () => {
  const parameters = kotlinParameters(`
    // fun Example(fake: String)
    @Composable fun Example(
      value: Map<String, Pair<Int, Int>>,
      action: (String, Int) -> Unit = { _, _ -> log("x, ) = y") },
      content: (@Composable RowScope.() -> Unit)? = null,
    ) { }
    fun Example(other: Boolean) { }
  `, 'Example');
  assert.deepEqual(parameters.map(p => p.name), ['value', 'action', 'content']);
  assert.equal(parameters[0].type, 'Map<String,Pair<Int,Int>>');
  assert.equal(parameters[1].defaultValue, '{_,_->log("x, ) = y")}');
  assert.equal(parameters[2].type, '(@ComposableRowScope.()->Unit)?');
});

test('metadata drift rejects changed names, types, default expressions and missing properties', () => {
  const source = 'fun Example(value: String, enabled: Boolean = true) {}';
  const properties = [{ name: 'value', type: 'String', defaultValue: 'required' }, { name: 'enabled', type: 'Boolean', defaultValue: 'true' }];
  verifyPropertyMetadata(source, { name: 'Example', properties });
  for (const patch of [{ name: 'active' }, { type: 'Int' }, { defaultValue: 'false' }]) {
    assert.throws(() => verifyPropertyMetadata(source, { name: 'Example', properties: [properties[0], { ...properties[1], ...patch }] }), /Registry API differs/);
  }
  assert.throws(() => verifyPropertyMetadata(source, { name: 'Example', properties: properties.slice(0, 1) }), /Registry API differs/);
});
