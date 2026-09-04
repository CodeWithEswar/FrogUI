// AUTO-GENERATED from registry and documentation content. DO NOT EDIT MANUALLY.
export interface ComponentProperty {
  name: string;
  type: string;
  defaultValue: string;
  description: string;
}

export interface ComponentExample {
  id: string;
  title: string;
  description: string;
  codeSnippet: string;
}

export interface ComponentAccessibility {
  role: string;
  minTouchTarget: string;
  talkBackNotes?: string;
}

export interface ComponentShowcase {
  route: string;
  source: string;
  screen: string;
}

export interface ComponentQuality {
  visualStates: string[];
  interactions: string[];
  themes: Array<'Light' | 'Dark' | 'Custom'>;
  adaptiveClasses: Array<'Compact' | 'Medium' | 'Expanded'>;
  composePreviews: string;
  unitTests: string[];
  androidTests: string[];
  webPreview: string;
}

export interface ComponentDocPage {
  id: string;
  name: string;
  displayName: string;
  description: string;
  category: string;
  status: 'stable' | 'beta' | 'experimental' | 'deprecated';
  since: string;
  path: string;
  variants: string[];
  sizes: string[];
  properties: ComponentProperty[];
  examples: ComponentExample[];
  tags: string[];
  accessibility: ComponentAccessibility;
  quality?: ComponentQuality;
  source?: string;
  showcase?: ComponentShowcase;
  prose: string;
}

export interface CategoryInfo {
  id: string;
  displayName: string;
  description: string;
}

export interface ReleaseInfo {
  version: string;
  versionCode: number;
  published: boolean;
}

export const release: ReleaseInfo = {
  "version": "0.1.0-SNAPSHOT",
  "versionCode": 1,
  "published": false
};

export const categories: CategoryInfo[] = [
  {
    "id": "actions",
    "displayName": "Actions",
    "description": "Interactive elements that trigger user operations, submissions, and state transitions."
  },
  {
    "id": "inputs",
    "displayName": "Inputs",
    "description": "Form controls, text fields, toggles, and selection controls for collecting user input."
  },
  {
    "id": "data-display",
    "displayName": "Data Display",
    "description": "Cards, badges, lists, and avatars for organizing and presenting application data."
  },
  {
    "id": "feedback",
    "displayName": "Feedback",
    "description": "Indicators, alerts, and progress states communicating status to the user."
  },
  {
    "id": "navigation",
    "displayName": "Navigation",
    "description": "Tabs, rails, bars, and segmented controls for hierarchical and peer navigation."
  },
  {
    "id": "overlays",
    "displayName": "Overlays",
    "description": "Modals, dialogs, bottom sheets, and tooltips presented above primary surfaces."
  },
  {
    "id": "layout",
    "displayName": "Layout",
    "description": "Structural scaffolds, spacers, containers, and responsive grids."
  }
];

export const catalog: ComponentDocPage[] = [
  {
    "id": "button",
    "name": "FrogButton",
    "displayName": "Button",
    "description": "Triggers an action with semantic variants, sizes, loading feedback, and composable content slots.",
    "category": "actions",
    "status": "experimental",
    "since": "0.1.0-SNAPSHOT",
    "path": "/FrogUI/components/button",
    "variants": [
      "Primary",
      "Secondary",
      "Outline",
      "Ghost",
      "Destructive"
    ],
    "sizes": [
      "Small",
      "Medium",
      "Large"
    ],
    "properties": [
      {
        "name": "onClick",
        "type": "() -> Unit",
        "defaultValue": "required",
        "description": "Callback triggered when the button is clicked by touch, keyboard, or accessibility action."
      },
      {
        "name": "modifier",
        "type": "Modifier",
        "defaultValue": "Modifier",
        "description": "Layout modifier applied to the outer touch-target container."
      },
      {
        "name": "variant",
        "type": "FrogButtonVariant",
        "defaultValue": "FrogButtonVariant.Primary",
        "description": "Visual semantic variant: Primary, Secondary, Outline, Ghost, or Destructive."
      },
      {
        "name": "size",
        "type": "FrogButtonSize",
        "defaultValue": "FrogButtonSize.Medium",
        "description": "Dimensional scale: Small (32dp), Medium (40dp), or Large (48dp)."
      },
      {
        "name": "enabled",
        "type": "Boolean",
        "defaultValue": "true",
        "description": "Controls interaction state. When false, clicks are suppressed and visual style becomes muted."
      },
      {
        "name": "loading",
        "type": "Boolean",
        "defaultValue": "false",
        "description": "Replaces visible content with a centered progress indicator while preserving measured label and slot bounds. Exposes Loading state and suppresses activation."
      },
      {
        "name": "shape",
        "type": "Shape",
        "defaultValue": "FrogButtonDefaults.shape(size)",
        "description": "Corner radius shape applied to button background and border."
      },
      {
        "name": "colors",
        "type": "FrogButtonColors",
        "defaultValue": "FrogButtonDefaults.colors(variant)",
        "description": "Resolved colors across enabled, disabled, pressed, and focused states."
      },
      {
        "name": "border",
        "type": "BorderStroke?",
        "defaultValue": "FrogButtonDefaults.border(colors, enabled)",
        "description": "Optional stroke border applied to button boundary."
      },
      {
        "name": "contentPadding",
        "type": "PaddingValues",
        "defaultValue": "FrogButtonDefaults.contentPadding(size)",
        "description": "Internal spacing between button boundary and content row."
      },
      {
        "name": "interactionSource",
        "type": "MutableInteractionSource",
        "defaultValue": "remember { MutableInteractionSource() }",
        "description": "Interaction stream driving press animations and focus rings."
      },
      {
        "name": "leadingIcon",
        "type": "(@Composable () -> Unit)?",
        "defaultValue": "null",
        "description": "Optional slot rendered before the button label."
      },
      {
        "name": "trailingIcon",
        "type": "(@Composable () -> Unit)?",
        "defaultValue": "null",
        "description": "Optional slot rendered after the button label."
      },
      {
        "name": "fullWidth",
        "type": "Boolean",
        "defaultValue": "false",
        "description": "Fills the available horizontal width of the button surface and touch target."
      },
      {
        "name": "content",
        "type": "@Composable RowScope.() -> Unit",
        "defaultValue": "required",
        "description": "The button label or custom row content."
      }
    ],
    "examples": [
      {
        "id": "primary",
        "title": "Primary Action",
        "description": "High-emphasis action for the primary task on the screen.",
        "codeSnippet": "@Composable\ninternal fun ButtonPrimaryExample(modifier: Modifier = Modifier) {\n    FrogButton(\n        onClick = {},\n        modifier = modifier,\n        variant = FrogButtonVariant.Primary\n    ) {\n        Text(\"Continue\")\n    }\n}"
      },
      {
        "id": "secondary",
        "title": "Secondary Action",
        "description": "Tonal zinc surface for alternative operations.",
        "codeSnippet": "@Composable\ninternal fun ButtonSecondaryExample(modifier: Modifier = Modifier) {\n    FrogButton(\n        onClick = {},\n        modifier = modifier,\n        variant = FrogButtonVariant.Secondary\n    ) {\n        Text(\"Cancel\")\n    }\n}"
      },
      {
        "id": "outline",
        "title": "Outlined Action",
        "description": "Transparent surface with structural Zinc border.",
        "codeSnippet": "@Composable\ninternal fun ButtonOutlineExample(modifier: Modifier = Modifier) {\n    FrogButton(\n        onClick = {},\n        modifier = modifier,\n        variant = FrogButtonVariant.Outline\n    ) {\n        Text(\"Documentation\")\n    }\n}"
      },
      {
        "id": "ghost",
        "title": "Ghost Action",
        "description": "Flat low-emphasis button for toolbars and compact surfaces.",
        "codeSnippet": "@Composable\ninternal fun ButtonGhostExample(modifier: Modifier = Modifier) {\n    FrogButton(\n        onClick = {},\n        modifier = modifier,\n        variant = FrogButtonVariant.Ghost\n    ) {\n        Text(\"Learn more\")\n    }\n}"
      },
      {
        "id": "destructive",
        "title": "Destructive Action",
        "description": "Communicates permanent or dangerous operations.",
        "codeSnippet": "@Composable\ninternal fun ButtonDestructiveExample(modifier: Modifier = Modifier) {\n    FrogButton(\n        onClick = {},\n        modifier = modifier,\n        variant = FrogButtonVariant.Destructive\n    ) {\n        Text(\"Delete repository\")\n    }\n}"
      },
      {
        "id": "loading",
        "title": "Loading State",
        "description": "Displays inline circular progress indicator and pauses user interaction.",
        "codeSnippet": "@Composable\ninternal fun ButtonLoadingExample(modifier: Modifier = Modifier) {\n    FrogButton(\n        onClick = {},\n        modifier = modifier,\n        variant = FrogButtonVariant.Primary,\n        loading = true\n    ) {\n        Text(\"Saving...\")\n    }\n}"
      },
      {
        "id": "leading",
        "title": "Leading icon",
        "description": "Run an action with a decorative leading icon.",
        "codeSnippet": "@Composable\ninternal fun ButtonLeadingExample(leadingIcon: ImageVector, modifier: Modifier = Modifier) {\n    FrogButton(onClick = {}, modifier = modifier, leadingIcon = { Icon(leadingIcon, null, Modifier.size(18.dp)) }) { Text(\"Run preview\") }\n}"
      },
      {
        "id": "trailing",
        "title": "Trailing icon",
        "description": "Indicate progression with a decorative trailing icon.",
        "codeSnippet": "@Composable\ninternal fun ButtonTrailingExample(trailingIcon: ImageVector, modifier: Modifier = Modifier) {\n    FrogButton(onClick = {}, modifier = modifier, trailingIcon = { Icon(trailingIcon, null, Modifier.size(18.dp)) }) { Text(\"Continue\") }\n}"
      },
      {
        "id": "disabled",
        "title": "Disabled action",
        "description": "An unavailable action exposes disabled semantics.",
        "codeSnippet": "@Composable\ninternal fun ButtonDisabledExample(modifier: Modifier = Modifier) {\n    FrogButton(onClick = {}, modifier = modifier, enabled = false) { Text(\"Unavailable\") }\n}"
      },
      {
        "id": "fullwidth",
        "title": "Full width",
        "description": "Fill the available horizontal space.",
        "codeSnippet": "@Composable\ninternal fun ButtonFullWidthExample(modifier: Modifier = Modifier) {\n    FrogButton(onClick = {}, modifier = modifier, fullWidth = true) { Text(\"Continue\") }\n}"
      }
    ],
    "tags": [
      "action",
      "submit",
      "loading"
    ],
    "accessibility": {
      "role": "Role.Button",
      "minTouchTarget": "48dp",
      "talkBackNotes": "Intended to expose button role, loading state, and disabled behavior. TalkBack, label grouping, localization, and actual touch bounds require verification before stability."
    },
    "quality": {
      "visualStates": [
        "Default",
        "Pressed",
        "Focused",
        "Disabled",
        "Loading"
      ],
      "interactions": [
        "Click",
        "Keyboard activation",
        "Focus"
      ],
      "themes": [
        "Light",
        "Dark",
        "Custom"
      ],
      "adaptiveClasses": [],
      "composePreviews": "app/src/main/java/io/github/codewitheswar/frogui/showcase/components/button/ButtonComponentPreviews.kt",
      "unitTests": [
        "frogui-components/src/test/java/io/github/codewitheswar/frogui/components/button/FrogButtonTest.kt"
      ],
      "androidTests": [
        "app/src/androidTest/java/io/github/codewitheswar/frogui/showcase/PublicApiContractTest.kt",
        "app/src/androidTest/java/io/github/codewitheswar/frogui/showcase/ButtonDetailTest.kt"
      ],
      "webPreview": "docs/src/components/preview/previews/button/ButtonPreview.tsx"
    },
    "source": "frogui-components/src/main/java/io/github/codewitheswar/frogui/components/button/FrogButton.kt",
    "showcase": {
      "route": "components/button",
      "source": "app/src/main/java/io/github/codewitheswar/frogui/showcase/components/button/ButtonShowcaseDefinition.kt",
      "screen": "buttonShowcaseDefinition"
    },
    "prose": "# Usage guidance\n\nUse Button to trigger an action such as saving a form or continuing a workflow.\nThe caller supplies the action callback and owns enabled/loading state. Loading\nsuppresses activation; it does not start network work or retain application state.\n\nWrap application content in `FrogTheme` from `io.github.codewitheswar.frogui.theme`.\nUse composable content and icon slots, native Modifier, semantic variant/size values,\nand FrogButtonColors for customization. API signatures, examples, capabilities, and\nstatus come from the generated registry rather than this prose.\n\n## Compose an action\n\nKeep application state with the caller. Pass a callback to the button and use its\ncontent slot for the visible label.\n\n```kotlin\n@Composable\nfun ContinueAction(enabled: Boolean, onContinue: () -> Unit) {\n    FrogButton(enabled = enabled, onClick = onContinue) {\n        Text(\"Continue\")\n    }\n}\n```\n\n### Choose a variant\n\n- **Primary** gives the main action the most emphasis.\n- **Secondary**, **Outline**, and **Ghost** support quieter actions.\n- **Destructive** identifies an action that needs destructive intent.\n\n> Loading blocks activation. The caller remains responsible for starting work,\n> handling its result, and updating `loading`.\n\n## Customize colors and layout\n\nUse `fullWidth = true` to expand both the visible surface and its touch target.\nSmall, Medium, and Large resolve visual heights and glyph sizes through `FrogTheme.sizing`.\nPadding and icon gaps remain component-specific. Use `FrogButtonDefaults.iconSize(size)`\nfor theme-aware icon slots. Targets use `FrogTheme.sizing.minimumTouchTarget`, at least\n48dp, separately from compact visuals. Prefer semantic shapes; override `shape` only\nwhen the surrounding design calls for it.\n\n```kotlin\nFrogButton(\n    variant = FrogButtonVariant.Outline,\n    fullWidth = true,\n    colors = FrogButtonDefaults.colors(\n        variant = FrogButtonVariant.Outline,\n        containerColor = FrogTheme.colors.surfaceElevated,\n        contentColor = FrogTheme.colors.foreground\n    ),\n    onClick = { /* Continue */ }\n) {\n    Text(\"Continue\")\n}\n```\n\nThe color defaults accept selected overrides, including border and disabled\ncolors. The default border uses the supplied `FrogButtonColors`. Keep the same\nvariant in the button and its color defaults. Semantic tokens follow the theme;\na custom literal such as `Color(0x8018181B)` keeps its ARGB value and alpha.\n\n`ProvideFrogThemeEnvironment(reduceMotion = true)` stops press scale and decorative\nloading rotation while retaining immediate state/color feedback. Android's disabled\nanimator preference also applies. Nested themes inherit omitted non-color token groups.\n\n## Use the component laboratory\n\n- Quick controls change variant and size immediately.\n- **Customize** opens grouped appearance, content, state, and color controls.\n- Wider layouts keep the inspector beside the preview.\n- Color rows open the same drawer in either theme-token or custom mode.\n- Drag the saturation/brightness plane, use the accessible sliders, or enter\n  `#RRGGBB` / `#AARRGGBB` (alpha first). Invalid input disables Apply.\n- A color draft updates the miniature button and live preview. **Apply** commits;\n  **Cancel**, Back, the close action, and outside dismissal leave committed colors\n  unchanged. Back returns to the parent inspector when opened from Customize.\n- **Reset** in a color editor drafts the current variant default. **Reset colors**\n  restores all colors without changing size, label, enabled, or loading state.\n- Changing variant restores its defaults. Generated Kotlin updates immediately,\n  preserving semantic token expressions.\n- Preview theme, width, background, and alignment are independent of exported code.\n- Tap an API property for its type, default, guidance, values, and a copyable example.\n\n## Accessibility\n\n- **Role and target:** one Button action with a minimum 48dp touch target. Surface\n  dimensions may be smaller for Small and Medium.\n- **Enabled:** disabling suppresses activation and exposes disabled semantics.\n- **Loading:** the visible label and icon slots are replaced by a centered spinner,\n  while retaining their measured bounds and accessible label. The state is Loading;\n  the spinner itself does not repeatedly announce progress. Activation is blocked.\n- **Focus:** keyboard focus draws a visible semantic focus ring. Use a shared\n  `MutableInteractionSource` when observing interactions.\n- **Label and icons:** use clear action text; decorative slots have a null content\n  description. Do not nest clickable controls inside the content.\n- **Contrast:** custom colors may reduce readability. The picker composites alpha\n  over the chosen opaque preview canvas, then calculates relative-luminance text\n  contrast. Its 4.5:1 normal-text feedback is informational, not a certification.\n  Transparent backdrops can change the result; inactive controls are exempt from\n  the minimum text contrast requirement. See the\n  [W3C contrast explanation](https://www.w3.org/WAI/WCAG22/Understanding/contrast-minimum.html).\n- **Text scaling:** content can grow vertically; inspector choices scroll rather\n  than truncate. Sliders expose labels, values, and adjustment actions.\n- **Motion:** the showcase respects its reduced-motion preference and the system\n  animator setting. Consumer apps can provide zero-duration `FrogMotion` tokens.\n\nAutomated semantics and interaction tests provide focused evidence. Human TalkBack\nspeech/traversal, physical tablet input, and minimum-API review remain release checks\nbefore this experimental component can be considered Stable.\n\n## Native preview\n\n### Icon-only companion\n\n`FrogIconButton(onClick, contentDescription)` shares Button's variants, sizes, colors,\nshape, border, enabled/loading states and interaction source. Its default variant is\nGhost, and its composable content is decorative. Supply a concise action label and\nnull descriptions on child icons. Loading keeps the action label, prevents repeated\nactivation and suppresses duplicate progress announcements. Custom pressed overlays\nand disabled borders use the supplied color contract. This public companion remains\nExperimental and is included in the components ABI baseline.\n\n### Compatibility\n\nThe established `leadingIcon` and `trailingIcon` slot names are retained for Kotlin\nnamed-argument compatibility. They accept composable content and do not require an\nicon library. `fullWidth` expands both the visible surface and outer target. A tiny\nlabel or reduced content padding still retains a 48dp target in each dimension unless\nan explicit parent constraint prevents it. See the library [API design guide](../../architecture/api-design.md)\nfor source, binary and behavior review rules.\n\nUse the Android Showcase for real interaction. Web documentation presents metadata,\nusage code, and documentation; it does not execute native Compose.\r\n"
  },
  {
    "id": "drawer",
    "name": "FrogDrawer",
    "displayName": "Drawer",
    "description": "Adaptive contextual overlay presented as a modal bottom sheet or docked side panel without navigating away from the current screen destination.",
    "category": "overlays",
    "status": "experimental",
    "since": "0.1.0-SNAPSHOT",
    "path": "/FrogUI/components/drawer",
    "variants": [
      "Auto",
      "Bottom",
      "Side"
    ],
    "sizes": [],
    "properties": [
      {
        "name": "state",
        "type": "FrogDrawerState",
        "defaultValue": "required",
        "description": "Hoisted requested visibility with a Saver and immediate open/close operations; values do not represent animation progress."
      },
      {
        "name": "onDismissRequest",
        "type": "() -> Unit",
        "defaultValue": "required",
        "description": "Requests closure on close, scrim tap, Back or drag. The caller must update state to dismiss."
      },
      {
        "name": "modifier",
        "type": "Modifier",
        "defaultValue": "Modifier",
        "description": "Layout modifier applied to the drawer container."
      },
      {
        "name": "presentation",
        "type": "FrogDrawerPresentation",
        "defaultValue": "FrogDrawerPresentation.Auto",
        "description": "Auto uses FrogTheme.adaptive and available host width: Bottom for Compact (below 600dp by default) and Side otherwise. Both are modal outside FrogOverlayHost."
      },
      {
        "name": "side",
        "type": "FrogDrawerSide",
        "defaultValue": "FrogDrawerSide.End",
        "description": "Docking edge for side presentation mode (Start or End)."
      },
      {
        "name": "title",
        "type": "String?",
        "defaultValue": "null",
        "description": "Title text rendered in the header with heading accessibility semantics."
      },
      {
        "name": "subtitle",
        "type": "String?",
        "defaultValue": "null",
        "description": "Subtitle text rendered underneath the title."
      },
      {
        "name": "navigationIcon",
        "type": "(@Composable () -> Unit)?",
        "defaultValue": "null",
        "description": "Optional slot rendered at the start of the header, typically a back navigation action."
      },
      {
        "name": "actions",
        "type": "(@Composable RowScope.() -> Unit)?",
        "defaultValue": "null",
        "description": "Optional trailing actions slot rendered in the header before the close button."
      },
      {
        "name": "preview",
        "type": "(@Composable () -> Unit)?",
        "defaultValue": "null",
        "description": "Optional live preview slot rendered directly below the header divider."
      },
      {
        "name": "footer",
        "type": "(@Composable () -> Unit)?",
        "defaultValue": "null",
        "description": "Optional sticky footer slot anchored below the scrollable content area."
      },
      {
        "name": "colors",
        "type": "FrogDrawerColors",
        "defaultValue": "FrogDrawerDefaults.colors()",
        "description": "Resolved colors for container, content, border, handle, and scrim."
      },
      {
        "name": "onBackRequest",
        "type": "(() -> Unit)?",
        "defaultValue": "null",
        "description": "Optional handler for system Back, such as returning to a parent inspector. Defaults to onDismissRequest when absent."
      },
      {
        "name": "closeIcon",
        "type": "(@Composable () -> Unit)?",
        "defaultValue": "null",
        "description": "Optional decorative glyph within the standard accessible close button."
      },
      {
        "name": "shape",
        "type": "Shape?",
        "defaultValue": "null",
        "description": "Optional surface shape override. Null resolves theme-aware corners after Auto chooses Bottom or Side presentation."
      },
      {
        "name": "content",
        "type": "@Composable ColumnScope.() -> Unit",
        "defaultValue": "required",
        "description": "The scrollable content body rendered within the drawer."
      }
    ],
    "examples": [
      {
        "id": "basic",
        "title": "Basic Drawer",
        "description": "Simple open and close with hoisted state.",
        "codeSnippet": "@Composable\ninternal fun DrawerBasicExample(modifier: Modifier = Modifier) {\n    val drawerState = rememberFrogDrawerState()\n    val scope = rememberCoroutineScope()\n\n    FrogButton(\n        onClick = { scope.launch { drawerState.open() } },\n        modifier = modifier\n    ) {\n        Text(\"Open Drawer\")\n    }\n\n    FrogDrawer(\n        state = drawerState,\n        onDismissRequest = { scope.launch { drawerState.close() } },\n        title = \"Settings\"\n    ) {\n        Text(\n            \"Configure application preferences and appearance settings.\",\n            style = FrogTheme.typography.body,\n            color = FrogTheme.colors.foreground\n        )\n    }\n}"
      },
      {
        "id": "bottom",
        "title": "Bottom Presentation",
        "description": "Compact modal bottom sheet with drag handle.",
        "codeSnippet": "@Composable\ninternal fun DrawerBottomExample(modifier: Modifier = Modifier) {\n    val drawerState = rememberFrogDrawerState()\n    val scope = rememberCoroutineScope()\n\n    FrogButton(\n        onClick = { scope.launch { drawerState.open() } },\n        modifier = modifier\n    ) {\n        Text(\"Open Bottom Sheet\")\n    }\n\n    FrogDrawer(\n        state = drawerState,\n        onDismissRequest = { scope.launch { drawerState.close() } },\n        presentation = FrogDrawerPresentation.Bottom,\n        title = \"Share Options\",\n        subtitle = \"Select destination\"\n    ) {\n        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {\n            Text(\"Copy direct link\", style = FrogTheme.typography.body, color = FrogTheme.colors.foreground)\n            Text(\"Export as JSON\", style = FrogTheme.typography.body, color = FrogTheme.colors.foreground)\n            Text(\"Send to device\", style = FrogTheme.typography.body, color = FrogTheme.colors.foreground)\n        }\n    }\n}"
      },
      {
        "id": "side",
        "title": "Side Inspector",
        "description": "Contextual end-docked tool panel.",
        "codeSnippet": "@Composable\ninternal fun DrawerSideExample(modifier: Modifier = Modifier) {\n    val drawerState = rememberFrogDrawerState()\n    val scope = rememberCoroutineScope()\n\n    FrogButton(\n        onClick = { scope.launch { drawerState.open() } },\n        modifier = modifier\n    ) {\n        Text(\"Open Side Inspector\")\n    }\n\n    FrogDrawer(\n        state = drawerState,\n        onDismissRequest = { scope.launch { drawerState.close() } },\n        presentation = FrogDrawerPresentation.Side,\n        side = FrogDrawerSide.End,\n        title = \"Component Properties\",\n        subtitle = \"Interactive configuration\"\n    ) {\n        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {\n            Text(\"Variant: Primary\", style = FrogTheme.typography.body, color = FrogTheme.colors.foreground)\n            Text(\"Size: Medium (40dp)\", style = FrogTheme.typography.body, color = FrogTheme.colors.foreground)\n            Text(\"Touch target: 48dp minimum\", style = FrogTheme.typography.bodySmall, color = FrogTheme.colors.mutedForeground)\n        }\n    }\n}"
      },
      {
        "id": "header",
        "title": "Custom Header Actions",
        "description": "Header with navigation and secondary reset action.",
        "codeSnippet": "@Composable\ninternal fun DrawerHeaderExample(modifier: Modifier = Modifier) {\n    val drawerState = rememberFrogDrawerState()\n    val scope = rememberCoroutineScope()\n\n    FrogButton(\n        onClick = { scope.launch { drawerState.open() } },\n        modifier = modifier\n    ) {\n        Text(\"Drawer With Custom Header\")\n    }\n\n    FrogDrawer(\n        state = drawerState,\n        onDismissRequest = { scope.launch { drawerState.close() } },\n        title = \"Filter Components\",\n        subtitle = \"3 filters applied\",\n        actions = {\n            FrogButton(\n                onClick = {},\n                variant = FrogButtonVariant.Ghost\n            ) {\n                Text(\"Reset\")\n            }\n        }\n    ) {\n        Text(\n            \"Filter items by category, capability, or status.\",\n            style = FrogTheme.typography.body,\n            color = FrogTheme.colors.foreground\n        )\n    }\n}"
      },
      {
        "id": "footer",
        "title": "Sticky Action Footer",
        "description": "Sticky Cancel and Apply buttons below scroll body.",
        "codeSnippet": "@Composable\ninternal fun DrawerFooterExample(modifier: Modifier = Modifier) {\n    val drawerState = rememberFrogDrawerState()\n    val scope = rememberCoroutineScope()\n\n    FrogButton(\n        onClick = { scope.launch { drawerState.open() } },\n        modifier = modifier\n    ) {\n        Text(\"Drawer With Action Footer\")\n    }\n\n    FrogDrawer(\n        state = drawerState,\n        onDismissRequest = { scope.launch { drawerState.close() } },\n        title = \"Confirm Changes\",\n        footer = {\n            Row(\n                modifier = Modifier.fillMaxWidth(),\n                horizontalArrangement = Arrangement.spacedBy(8.dp)\n            ) {\n                FrogButton(\n                    onClick = { scope.launch { drawerState.close() } },\n                    modifier = Modifier.weight(1f),\n                    variant = FrogButtonVariant.Secondary\n                ) {\n                    Text(\"Cancel\")\n                }\n                FrogButton(\n                    onClick = { scope.launch { drawerState.close() } },\n                    modifier = Modifier.weight(1f),\n                    variant = FrogButtonVariant.Primary\n                ) {\n                    Text(\"Apply\")\n                }\n            }\n        }\n    ) {\n        Text(\n            \"Review your configuration before saving changes to the project.\",\n            style = FrogTheme.typography.body,\n            color = FrogTheme.colors.foreground\n        )\n    }\n}"
      },
      {
        "id": "scroll",
        "title": "Scrollable Content Body",
        "description": "Long form content testing scroll containment.",
        "codeSnippet": "@Composable\ninternal fun DrawerScrollExample(modifier: Modifier = Modifier) {\n    val drawerState = rememberFrogDrawerState()\n    val scope = rememberCoroutineScope()\n\n    FrogButton(\n        onClick = { scope.launch { drawerState.open() } },\n        modifier = modifier\n    ) {\n        Text(\"Scrollable Content Drawer\")\n    }\n\n    FrogDrawer(\n        state = drawerState,\n        onDismissRequest = { scope.launch { drawerState.close() } },\n        title = \"Changelog & Release Notes\",\n        subtitle = \"Recent library updates\"\n    ) {\n        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {\n            repeat(15) { index ->\n                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {\n                    Text(\n                        \"Release v0.1.${index + 1}\",\n                        style = FrogTheme.typography.heading,\n                        color = FrogTheme.colors.foreground\n                    )\n                    Text(\n                        \"Detailed release notes explaining architectural refinements, accessibility audits, and Compose compatibility improvements.\",\n                        style = FrogTheme.typography.bodySmall,\n                        color = FrogTheme.colors.mutedForeground\n                    )\n                }\n            }\n        }\n    }\n}"
      }
    ],
    "tags": [
      "overlay",
      "sheet",
      "side-panel",
      "inspector",
      "modal",
      "dialog"
    ],
    "accessibility": {
      "role": "Pane",
      "minTouchTarget": "48dp",
      "talkBackNotes": "Pane title and dismiss action are implemented. Native modal windows contain focus; embedded hosts provide a bounded nonmodal region. Human TalkBack speech and traversal need release verification."
    },
    "quality": {
      "visualStates": [
        "Closed",
        "Open",
        "Short content",
        "Scrollable content"
      ],
      "interactions": [
        "Open",
        "Dismiss",
        "System Back",
        "Scrim",
        "Focus",
        "Drag",
        "Scroll"
      ],
      "themes": [
        "Light",
        "Dark",
        "Custom"
      ],
      "adaptiveClasses": [
        "Compact",
        "Medium",
        "Expanded"
      ],
      "composePreviews": "app/src/main/java/io/github/codewitheswar/frogui/showcase/components/drawer/DrawerDetailPreviews.kt",
      "unitTests": [
        "frogui-components/src/test/java/io/github/codewitheswar/frogui/components/overlays/drawer/FrogDrawerTest.kt"
      ],
      "androidTests": [
        "app/src/androidTest/java/io/github/codewitheswar/frogui/showcase/PublicApiContractTest.kt",
        "app/src/androidTest/java/io/github/codewitheswar/frogui/showcase/SharedComponentDetailTest.kt"
      ],
      "webPreview": "docs/src/components/preview/previews/drawer/DrawerPreview.tsx"
    },
    "source": "frogui-components/src/main/java/io/github/codewitheswar/frogui/components/overlays/drawer/FrogDrawer.kt",
    "showcase": {
      "route": "components/drawer",
      "source": "app/src/main/java/io/github/codewitheswar/frogui/showcase/components/drawer/DrawerShowcaseDefinition.kt",
      "screen": "drawerShowcaseDefinition"
    },
    "prose": "# Usage guidance\n\nUse Drawer to present contextual content, secondary workflows, and property inspectors without navigating away from the current screen destination.\nThe caller owns the drawer state and supplies the dismiss callback. Dismiss gestures include modal backdrop taps, system back gestures, and downward drag gestures on compact bottom presentations.\n\n`rememberFrogDrawerState()` is optional: use the `visible` overload if the application already owns a Boolean. The helper saves requested visibility. Its suspend `open()` and `close()` functions update state immediately; they do not wait for visual animation to finish. `snapTo()` also changes requested visibility, and does not bypass rendering motion.\n\nWrap application content in `FrogTheme` from `io.github.codewitheswar.frogui.theme`.\nUse composable content, header, preview, and sticky footer slots, adaptive presentation modes, and `FrogDrawerColors` for customization. API signatures, examples, capabilities, and status come directly from the generated registry.\n\n## Compose a drawer\n\nHoisting state allows external triggers (such as toolbar buttons or menu items) to imperatively launch and dismiss the drawer.\n\n```kotlin\n@Composable\nfun SettingsDrawer() {\n    val drawerState = rememberFrogDrawerState()\n    val scope = rememberCoroutineScope()\n\n    FrogButton(\n        onClick = { scope.launch { drawerState.open() } }\n    ) {\n        Text(\"Configure Settings\")\n    }\n\n    FrogDrawer(\n        state = drawerState,\n        onDismissRequest = { scope.launch { drawerState.close() } },\n        title = \"Settings\",\n        subtitle = \"Manage application preferences\"\n    ) {\n        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {\n            Text(\"General Preferences\", style = FrogTheme.typography.heading)\n            Text(\"Notification and appearance settings.\")\n        }\n    }\n}\n```\n\n### Choose an adaptive presentation\n\n- **Auto** (default) resolves from `FrogTheme.adaptive`: Compact (below 600dp by default) presents as a bottom sheet; Medium/Expanded use a side panel. Both presentations use a native modal window by default.\n- **Bottom** forces bottom sheet presentation with top rounded corners and an interactive drag-to-dismiss handle indicator.\n- **Side** places the panel at a logical screen edge (`FrogDrawerSide.End` or `FrogDrawerSide.Start`). Its width is capped by the available space. Start and End mirror in RTL.\n\nOn bottom presentation, a downward handle drag exceeding 64dp requests dismissal. The owner closes the state in `onDismissRequest`.\n\n### Preview within a bounded workspace\n\nWrap content in `FrogOverlayHost(Modifier.width(360.dp).height(360.dp))` to render the same public drawer inside explicit bounds. Auto uses the host's width, and the panel and scrim stay inside those bounds. This is useful for component previews; it does not create a modal window or trap focus across the application. The host's caller handles Back and restores focus to its trigger.\n\nThe showcase uses this bounded host in the shared Preview workspace. Its compiled examples open native modal windows. Preview theme, width and background are independent from generated component code. The shared detail tabs are Preview, Code, API and Accessibility; usage guidance and examples live in Preview.\n\n## Customize slots and layout\n\nFrogDrawer provides dedicated structural slots:\n- **Header**: Includes title, subtitle, optional `navigationIcon` (e.g. back navigation), and optional trailing `actions`.\n- **Preview**: Rendered immediately below the header divider for live preview canvases or status badges.\n- **Content**: The main scrollable body. Internal scroll state is managed automatically so headers and footers remain pinned.\n- **Footer**: Sticky bottom bar rendered outside the scrollable body, typically housing primary and secondary action buttons.\n\nThe showcase's footer and long-content switches configure these slots; they are demonstration options, not extra public Drawer parameters.\n\n### Customize the surface\n\nLeave `shape = null` to resolve theme-aware corners after Auto chooses Bottom or Side.\nPass a shape when the surrounding product surface needs a deliberate override. Use\n`FrogDrawerDefaults.colors()` for selected color overrides so omitted fields continue\nto follow `FrogTheme`.\n\n```kotlin\nFrogDrawer(\n    state = drawerState,\n    onDismissRequest = { scope.launch { drawerState.close() } },\n    shape = RoundedCornerShape(28.dp),\n    colors = FrogDrawerDefaults.colors(\n        containerColor = FrogTheme.colors.surfaceElevated,\n        borderColor = FrogTheme.colors.primary\n    )\n) {\n    Text(\"Customized contextual content\")\n}\n```\n\nThe Showcase Appearance inspector uses the shared `FrogColorPicker`. Draft colors\nupdate the actual Drawer preview; Apply commits the draft, while Cancel, Back, and\noutside dismissal leave the previous value intact. Shape and color controls generate\nonly real public parameters.\n\n### Migrating the Boolean-side overload\n\nThe older overload with `side: Boolean` and `onBack` remains callable with a deprecation warning. In the canonical overload, pass `presentation = if (side) FrogDrawerPresentation.Side else FrogDrawerPresentation.Bottom` to preserve its placement. Move its old `actions` lambda into a `Row` in `footer`. Supply the back button through `navigationIcon` and the same callback through `onBackRequest`. The canonical `actions` slot is in the header, so moving old actions there changes behavior.\n\n`FrogDrawerDefaults.AnimationDurationMs` is also deprecated: it never controls the current renderer. Set `FrogTheme` motion tokens to configure transitions. Zero normal duration disables Drawer transitions; the configured normal duration and enter/exit easing otherwise apply directly. There is no mechanical `ReplaceWith` because these migrations require a behavior choice.\n\n```kotlin\nFrogDrawer(\n    state = drawerState,\n    onDismissRequest = { scope.launch { drawerState.close() } },\n    title = \"Edit Profile\",\n    footer = {\n        Row(\n            modifier = Modifier.fillMaxWidth(),\n            horizontalArrangement = Arrangement.spacedBy(8.dp)\n        ) {\n            FrogButton(\n                onClick = { scope.launch { drawerState.close() } },\n                modifier = Modifier.weight(1f),\n                variant = FrogButtonVariant.Secondary\n            ) {\n                Text(\"Cancel\")\n            }\n            FrogButton(\n                onClick = { scope.launch { drawerState.close() } },\n                modifier = Modifier.weight(1f),\n                variant = FrogButtonVariant.Primary\n            ) {\n                Text(\"Save\")\n            }\n        }\n    }\n) {\n    Text(\"User profile form fields and configurations.\")\n}\n```\n\n## Accessibility\n\n- **Pane Semantics**: Automatically declares `paneTitle = title ?: \"Drawer\"` so screen readers announce window transitions when opened.\n- **Semantic Dismissal**: Exposes the standard accessibility dismiss action to assistive tools.\n- **Focus management**: Native modal windows contain focus, and the close control receives initial keyboard focus. Embedded previews use the host's focus boundary; the showcase returns focus to the launch button after dismissal.\n- **Touch targets**: The built-in close and compatibility back buttons use `FrogIconButton` with `FrogTheme.sizing.minimumTouchTarget` (48dp by default). Caller-supplied navigation and action slots must preserve their own targets and accessible labels.\n- **Nested pages**: `onBackRequest` can return to a parent inspector while Close and outside dismissal discard the entire contextual flow. Without it, Back calls `onDismissRequest`.\n- **Motion and insets**: Zero-duration theme motion removes transitions. Native modal content respects safe-drawing and keyboard insets; the body scrolls beneath the header and above the footer.\n\nDrawer remains Experimental. Human TalkBack speech and traversal, physical keyboard/tablet behavior and hinge-aware placement still require release review. Automated semantics and layout checks do not replace those checks.\n"
  }
];

export function getComponentById(id: string): ComponentDocPage | undefined {
  return catalog.find(c => c.id === id);
}

export function getComponentsByCategory(categoryId: string): ComponentDocPage[] {
  return catalog.filter(c => c.category === categoryId);
}
