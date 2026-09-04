import test from 'node:test';
import assert from 'node:assert/strict';
import fs from 'node:fs';
import { verifyThemeColors } from './verify.mjs';

test('web semantic colors agree with both canonical Kotlin palettes', () => {
  assert.equal(verifyThemeColors(), 32);
});
test('a changed or missing CSS token fails the drift gate', () => {
  const css = fs.readFileSync(new URL('../../docs/src/index.css', import.meta.url), 'utf8');
  assert.throws(() => verifyThemeColors(css.replace('--frog-primary: #09090b;', '--frog-primary: #123456;')), /light --frog-primary/);
  assert.throws(() => verifyThemeColors(css.replace('--frog-destructive: #dc2626;', '')), /light --frog-destructive/);
});
