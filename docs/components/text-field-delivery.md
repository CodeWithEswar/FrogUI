# FrogTextField delivery record

- Component: `FrogTextField`
- Registry ID: `text-field`
- Category: `inputs`
- Current status: `Experimental`
- Last reviewed: `2026-09-05`

## Specification

FrogTextField represents the canonical editable text input component for FrogUI forms and inputs.
It supports three visual presentations: Filled (subtle container surface with bottom indicator),
Outline (clear border container boundary), and Underline (minimal surface with bottom border emphasis).
State hoisting is strictly preserved: the caller owns `value: String` and `onValueChange: (String) -> Unit`.
The label model guarantees persistent or floating label association ensuring assistive technologies
and sighted users can identify the field at all times without relying on placeholder text alone.
Supporting text prioritizes `errorText` over `helperText`, automatically attaching error styling and
accessibility error semantics without TalkBack announcement spam. Interaction states cover default,
focused, error, disabled (`enabled = false`), and read-only (`readOnly = true`). Sizing provides a
guaranteed minimum interactive height (56dp standard, 48dp compact) across all variants.
Reduced-motion user preferences are strictly honored via immediate color and position snaps.

## API review

`value` and `onValueChange` express caller state ownership. Modifier controls the outer container layout;
`label`, `placeholder`, `helperText`, `errorText`, `leading`, and `trailing` follow as semantic content
and slot parameters. Variant defaults to `FrogTextFieldVariant.Filled`. `enabled`, `readOnly`, `singleLine`,
`maxLines`, `keyboardOptions`, `keyboardActions`, and `visualTransformation` control input behavior.
Styling and advanced interaction are handled by `colors`, `shape`, and `interactionSource`.
All parameter types are fully independent of third-party icon or styling libraries.

## Delivery gates

| Gate | Audit | Evidence |
| --- | --- | --- |
| Specification | COMPLETE | This record defines purpose, state ownership, Filled/Outline/Underline variants, label association, supporting text, and accessibility. |
| Public API | COMPLETE | Canonical FrogTextField, FrogTextFieldVariant, FrogTextFieldColors, and Defaults have KDoc and registry-verified signatures. |
| Implementation | COMPLETE | The library component is Compose-native, uses BasicTextField with custom decoration, and owns all tokens and semantics. |
| Theme / states | COMPLETE | Defaults resolve FrogTheme tokens for colors, typography, shapes, sizing, focus border/indicator, and reduced motion. |
| Accessibility | PARTIAL | Persistent label association, error semantics, disabled/read-only distinction, and touch target minimums automated; human TalkBack QA pending. |
| Compose previews | COMPLETE | FrogTextFieldPreviews renders Default, Variants, States, Slots, Multiline, and Dark theme in library and showcase preview galleries. |
| Tests | PARTIAL | Unit tests verify dimensions, colors, and variants; Compose UI tests verify live typing, variant switching, error semantics, and states. |
| Registry | COMPLETE | text-field.json contains canonical API properties, examples, accessibility facts, and schema-v2 quality metadata. |
| Showcase | COMPLETE | TextFieldShowcaseDefinition supplies the live preview, typed controls, examples, code generation, API, and accessibility tabs. |
| Web docs | COMPLETE | Shared ComponentDetailPage renders text-field.md and the isolated registered TextFieldPreview. |
| API compatibility | COMPLETE | New public types ready for apiDump; baseline validation checks in place. |
| Visual regression | PARTIAL | Previews and inspector previews implemented; golden baseline images remain deferred. |

## Promotion decision

FrogTextField is introduced as Experimental. Its implementation pipeline is complete across library,
showcase, registry, and documentation, but broad manual TalkBack verification, physical IME input QA,
and reviewed visual regression baselines are required before promoting to Beta or Stable.
