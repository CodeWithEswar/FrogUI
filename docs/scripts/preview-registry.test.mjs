import test from 'node:test';
import assert from 'node:assert/strict';
import fs from 'node:fs';
import path from 'node:path';
import { fileURLToPath } from 'node:url';

const root = path.resolve(path.dirname(fileURLToPath(import.meta.url)), '../..');

test('preview registry declares definitions for all registered components', () => {
  const registryTs = fs.readFileSync(
    path.join(root, 'docs/src/components/preview/ComponentPreviewRegistry.ts'),
    'utf-8'
  );

  // Verify button and drawer are mapped
  assert.match(registryTs, /button:\s*\{/);
  assert.match(registryTs, /drawer:\s*\{/);
  assert.match(registryTs, /component:\s*ButtonPreview/);
  assert.match(registryTs, /component:\s*DrawerPreview/);

  // Verify helper exports exist
  assert.match(registryTs, /export function getComponentPreview/);
  assert.match(registryTs, /export function hasComponentPreview/);
});

test('per-component preview modules exist and isolate component JSX', () => {
  const buttonPreviewPath = path.join(
    root,
    'docs/src/components/preview/previews/button/ButtonPreview.tsx'
  );
  const drawerPreviewPath = path.join(
    root,
    'docs/src/components/preview/previews/drawer/DrawerPreview.tsx'
  );

  assert.equal(fs.existsSync(buttonPreviewPath), true);
  assert.equal(fs.existsSync(drawerPreviewPath), true);

  const buttonContent = fs.readFileSync(buttonPreviewPath, 'utf-8');
  const drawerContent = fs.readFileSync(drawerPreviewPath, 'utf-8');

  // Button owns Button-specific state
  assert.match(buttonContent, /variant/);
  assert.match(buttonContent, /ButtonPreview/);

  // Drawer owns Drawer-specific state
  assert.match(drawerContent, /presentation/);
  assert.match(drawerContent, /DrawerPreview/);
  assert.match(drawerContent, /dragOffset/);
});
