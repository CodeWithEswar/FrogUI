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
  },
  {
    "id": "fab",
    "name": "FrogFloatingActionButton",
    "displayName": "Floating Action Button",
    "description": "A prominent floating contextual action with regular, small, and extended presentations, accessible naming, and reduced-motion fallbacks.",
    "category": "actions",
    "status": "experimental",
    "since": "0.1.0-SNAPSHOT",
    "path": "/FrogUI/components/fab",
    "variants": [
      "Regular",
      "Small",
      "Extended"
    ],
    "sizes": [
      "Small",
      "Regular"
    ],
    "properties": [
      {
        "name": "icon",
        "type": "@Composable () -> Unit",
        "defaultValue": "required",
        "description": "Primary visual glyph composable. Should use contentDescription = null since the parent button owns the accessible name."
      },
      {
        "name": "contentDescription",
        "type": "String",
        "defaultValue": "required",
        "description": "Mandatory concise action description announced by accessibility services."
      },
      {
        "name": "onClick",
        "type": "() -> Unit",
        "defaultValue": "required",
        "description": "Callback invoked when the button is activated via touch, keyboard, or accessibility action."
      },
      {
        "name": "modifier",
        "type": "Modifier",
        "defaultValue": "Modifier",
        "description": "Layout modifier applied to the outer touch-target container."
      },
      {
        "name": "presentation",
        "type": "FrogFabPresentation",
        "defaultValue": "FrogFabPresentation.Regular",
        "description": "Visual presentation form: Regular (56dp), Small (40dp), or Extended (48dp height)."
      },
      {
        "name": "label",
        "type": "(@Composable () -> Unit)?",
        "defaultValue": "null",
        "description": "Composable visible text label used when presentation is Extended."
      },
      {
        "name": "expanded",
        "type": "Boolean",
        "defaultValue": "true",
        "description": "When true and presentation is Extended, displays both icon and label. When false, collapses to icon-only. Ignored for Regular and Small."
      },
      {
        "name": "enabled",
        "type": "Boolean",
        "defaultValue": "true",
        "description": "Controls interaction state. When false, clicks are suppressed and visual style becomes muted."
      },
      {
        "name": "visible",
        "type": "Boolean",
        "defaultValue": "true",
        "description": "Controls presence. When false, smoothly animates out and is removed from semantics and interaction."
      },
      {
        "name": "elevation",
        "type": "FrogFabElevation",
        "defaultValue": "FrogFloatingActionButtonDefaults.elevation()",
        "description": "Elevation tokens controlling shadow and surface separation across resting, pressed, and focused states."
      },
      {
        "name": "colors",
        "type": "FrogFabColors",
        "defaultValue": "FrogFloatingActionButtonDefaults.colors()",
        "description": "Resolved container, content, disabled, and interaction colors."
      },
      {
        "name": "shape",
        "type": "Shape",
        "defaultValue": "FrogFloatingActionButtonDefaults.shape(presentation)",
        "description": "Corner radius shape applied to the FAB container."
      },
      {
        "name": "interactionSource",
        "type": "MutableInteractionSource",
        "defaultValue": "remember { MutableInteractionSource() }",
        "description": "Interaction stream driving press animations and focus rings."
      }
    ],
    "examples": [
      {
        "id": "regular",
        "title": "Regular FAB",
        "description": "Default prominent floating action button for screen-level actions.",
        "codeSnippet": "FrogFloatingActionButton(\n    icon = { Icon(FrogIcons.Add, null) },\n    contentDescription = \"Create new item\",\n    onClick = { /* Handle action */ },\n    presentation = FrogFabPresentation.Regular\n)"
      },
      {
        "id": "small",
        "title": "Small FAB",
        "description": "Compact floating action button maintaining the 48dp minimum touch target.",
        "codeSnippet": "FrogFloatingActionButton(\n    icon = { Icon(FrogIcons.Search, null) },\n    contentDescription = \"Quick search\",\n    onClick = { /* Handle action */ },\n    presentation = FrogFabPresentation.Small\n)"
      },
      {
        "id": "extended",
        "title": "Extended FAB",
        "description": "Floating action with icon and visible text label.",
        "codeSnippet": "FrogFloatingActionButton(\n    icon = { Icon(FrogIcons.Add, null) },\n    label = { Text(\"Compose message\") },\n    contentDescription = \"Compose message\",\n    onClick = { /* Handle action */ },\n    presentation = FrogFabPresentation.Extended\n)"
      },
      {
        "id": "collapsing",
        "title": "Collapsing Extended FAB",
        "description": "Extended FAB with caller-toggled expansion state.",
        "codeSnippet": "var isExpanded by remember { mutableStateOf(true) }\n\nColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {\n    FrogFloatingActionButton(\n        icon = { Icon(FrogIcons.Add, null) },\n        label = { Text(\"New task\") },\n        contentDescription = \"New task\",\n        onClick = { /* Handle action */ },\n        presentation = FrogFabPresentation.Extended,\n        expanded = isExpanded\n    )\n    FrogButton(\n        onClick = { isExpanded = !isExpanded },\n        variant = FrogButtonVariant.Outline,\n        size = FrogButtonSize.Small\n    ) {\n        Text(if (isExpanded) \"Collapse FAB\" else \"Expand FAB\")\n    }\n}"
      },
      {
        "id": "scroll-aware",
        "title": "Scroll-Aware FAB",
        "description": "Demonstrates screen-driven visibility and expansion based on list scroll direction.",
        "codeSnippet": "val listState = rememberLazyListState()\nval isScrollingUp by remember {\n    derivedStateOf {\n        listState.firstVisibleItemIndex == 0 ||\n            listState.firstVisibleItemScrollOffset == 0\n    }\n}\n\nBox(\n    modifier = Modifier\n        .fillMaxWidth()\n        .height(180.dp)\n        .background(FrogTheme.colors.surface)\n        .padding(8.dp)\n) {\n    LazyColumn(state = listState, modifier = Modifier.fillMaxWidth()) {\n        items((1..15).toList()) { index ->\n            Text(\n                text = \"Activity feed item #$index\",\n                style = FrogTheme.typography.bodySmall,\n                color = FrogTheme.colors.foreground,\n                modifier = Modifier.padding(vertical = 6.dp)\n            )\n        }\n    }\n    FrogFloatingActionButton(\n        icon = { Icon(FrogIcons.Add, null) },\n        label = { Text(\"Add update\") },\n        contentDescription = \"Add update\",\n        onClick = { },\n        presentation = FrogFabPresentation.Extended,\n        expanded = isScrollingUp,\n        modifier = Modifier.align(Alignment.BottomEnd).padding(8.dp)\n    )\n}"
      },
      {
        "id": "inset-aware",
        "title": "Inset-Aware Placement",
        "description": "Positioning a FAB at the bottom-end above navigation bars and system insets.",
        "codeSnippet": "Box(\n    modifier = Modifier\n        .fillMaxWidth()\n        .height(140.dp)\n        .background(FrogTheme.colors.surface)\n        .padding(12.dp)\n) {\n    Text(\n        text = \"Screen content area\",\n        style = FrogTheme.typography.body,\n        color = FrogTheme.colors.mutedForeground\n    )\n    // Positioned at bottom-end above navigation bar/insets\n    FrogFloatingActionButton(\n        icon = { Icon(FrogIcons.Add, null) },\n        contentDescription = \"Floating creation action\",\n        onClick = { },\n        presentation = FrogFabPresentation.Regular,\n        modifier = Modifier\n            .align(Alignment.BottomEnd)\n            .padding(bottom = 8.dp, end = 8.dp)\n    )\n}"
      }
    ],
    "tags": [
      "action",
      "fab",
      "floating",
      "extended",
      "speeddial"
    ],
    "accessibility": {
      "role": "Role.Button",
      "minTouchTarget": "48dp",
      "talkBackNotes": "contentDescription is mandatory for all presentations. When hidden, the button is removed completely from the accessibility tree and interaction. Minimum 48dp touch target is guaranteed even for Small FAB."
    },
    "quality": {
      "visualStates": [
        "Default",
        "Pressed",
        "Focused",
        "Disabled",
        "Hidden",
        "Expanded",
        "Collapsed"
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
      "composePreviews": "app/src/main/java/io/github/codewitheswar/frogui/showcase/components/fab/FabComponentPreviews.kt",
      "unitTests": [
        "frogui-components/src/test/java/io/github/codewitheswar/frogui/components/fab/FrogFloatingActionButtonTest.kt"
      ],
      "androidTests": [
        "app/src/androidTest/java/io/github/codewitheswar/frogui/showcase/PublicApiContractTest.kt",
        "app/src/androidTest/java/io/github/codewitheswar/frogui/showcase/FabDetailTest.kt"
      ],
      "webPreview": "docs/src/components/preview/previews/fab/FabPreview.tsx"
    },
    "source": "frogui-components/src/main/java/io/github/codewitheswar/frogui/components/fab/FrogFloatingActionButton.kt",
    "showcase": {
      "route": "components/fab",
      "source": "app/src/main/java/io/github/codewitheswar/frogui/showcase/components/fab/FabShowcaseDefinition.kt",
      "screen": "fabShowcaseDefinition"
    },
    "prose": "# Usage guidance\n\nUse `FrogFloatingActionButton` (FAB) for prominent contextual actions that float above the main content canvas. Typical examples include creating a new record, composing a message, scanning a document, or starting a capture. The component emphasizes the single most important action for the current screen context.\n\nWrap application content in `FrogTheme` from `io.github.codewitheswar.frogui.theme`.\nUse composable icon and label slots, standard Compose `Modifier`, presentation forms (`FrogFabPresentation`), and semantic elevation defaults.\n\n## Compose a floating action\n\nKeep application state with the caller. Supply a descriptive action label that explains what happens when activated, rather than describing the visual icon glyph:\n\n```kotlin\n@Composable\nfun CreateItemFab(onCreate: () -> Unit) {\n    FrogFloatingActionButton(\n        icon = {\n            Icon(\n                imageVector = FrogIcons.Add,\n                contentDescription = null\n            )\n        },\n        contentDescription = \"Create new document\",\n        onClick = onCreate\n    )\n}\n```\n\n> **Accessibility Rule:** `contentDescription` is mandatory for all presentations of `FrogFloatingActionButton`. Child icon composables passed into the `icon` slot should specify `contentDescription = null` so that assistive technologies announce the action once from the parent button without duplicate or confusing descriptions.\n\n### When to use\n\n- One action deserves persistent visual prominence on the screen.\n- The action belongs to the current screen context.\n- Floating placement improves discoverability and thumb reach.\n- The action remains understandable through the icon and/or label.\n\n### When not to use\n\n- Multiple actions on the screen have equal importance.\n- The action belongs naturally in a form footer or modal bottom bar.\n- The action is destructive (e.g. Delete, Erase). Use a confirmation dialog with `FrogButton(variant = Destructive)` instead.\n- The screen already has an unambiguous primary `FrogButton`.\n- Floating placement would cover critical interactive content or text.\n- The action is global navigation. Use a Navigation Bar or Navigation Rail instead.\n\n---\n\n## Presentations\n\n`FrogFloatingActionButton` supports three semantic presentation forms:\n\n- **Regular:** Canonical 56dp square visual container with a 24dp centered icon. Designed as the default floating primary action.\n- **Small:** Compact 40dp square visual container with a 20dp centered icon. Suitable for secondary floating actions or dense tablet layouts. Preserves a **guaranteed 48dp minimum interactive touch target** for motor accessibility.\n- **Extended:** 48dp height container presenting an icon alongside a visible text label. Ideal when an icon alone might be ambiguous or when stronger affordance is required.\n\n```kotlin\n// Extended FAB with dynamic label expansion\nFrogFloatingActionButton(\n    icon = { Icon(FrogIcons.Add, null) },\n    label = { Text(\"Compose message\") },\n    contentDescription = \"Compose message\",\n    onClick = { },\n    presentation = FrogFabPresentation.Extended,\n    expanded = true\n)\n```\n\n---\n\n## Extended expansion & collapsing\n\nExtended FABs can dynamically collapse to an icon-only representation to optimize screen real estate during content consumption:\n\n```kotlin\nvar isExpanded by remember { mutableStateOf(true) }\n\nFrogFloatingActionButton(\n    icon = { Icon(FrogIcons.Add, null) },\n    label = { Text(\"Create task\") },\n    contentDescription = \"Create task\",\n    onClick = { },\n    presentation = FrogFabPresentation.Extended,\n    expanded = isExpanded\n)\n```\n\nWhen `expanded` transitions between `true` and `false`:\n- The container width smoothly animates using `FrogTheme.motion.normalSpec()`.\n- The label cleanly appears or disappears without clipping adjacent content.\n- If the user has requested reduced motion (`FrogTheme.motion.isReduced`), the layout snaps instantly without spatial animation.\n\n---\n\n## Visibility & presence\n\nVisibility is controlled by the caller through the `visible` boolean property:\n\n```kotlin\n// Visibility derived from screen state (e.g. scroll direction)\nFrogFloatingActionButton(\n    icon = { Icon(FrogIcons.Add, null) },\n    contentDescription = \"New item\",\n    onClick = { },\n    visible = shouldShowFab\n)\n```\n\n> **Semantic Removal:** When `visible == false`, the button is smoothly animated out and **completely removed from accessibility semantics and touch interaction**. It cannot receive clicks or keyboard focus while hidden.\n\n---\n\n## Elevation & dark theme\n\nResting, pressed, and focused elevations are governed by `FrogFabElevation`, resolving by default from `FrogTheme.elevation`:\n\n- **Resting:** `FrogTheme.elevation.medium` (3dp).\n- **Pressed:** `FrogTheme.elevation.high` (6dp) with a subtle tonal press overlay.\n- **Focused:** Highlights with a 2dp high-contrast `focusRingColor`.\n- **Dark Theme:** In dark mode, elevation is supplemented by a 1px subtle surface border (`FrogTheme.colors.border`) to ensure clear boundary definition on deep canvases.\n"
  },
  {
    "id": "icon-button",
    "name": "FrogIconButton",
    "displayName": "Icon Button",
    "description": "Compact icon-only action control with semantic variants, accessible labeling, loading support, and optional badge content.",
    "category": "actions",
    "status": "experimental",
    "since": "0.1.0-SNAPSHOT",
    "path": "/FrogUI/components/icon-button",
    "variants": [
      "Filled",
      "Tonal",
      "Outline",
      "Ghost"
    ],
    "sizes": [
      "Small",
      "Medium",
      "Large"
    ],
    "properties": [
      {
        "name": "icon",
        "type": "@Composable () -> Unit",
        "defaultValue": "required",
        "description": "The visual icon content. Should be decorative from the semantics tree as the parent IconButton provides the accessible name."
      },
      {
        "name": "contentDescription",
        "type": "String",
        "defaultValue": "required",
        "description": "Mandatory accessible description describing the action performed when activated."
      },
      {
        "name": "onClick",
        "type": "() -> Unit",
        "defaultValue": "required",
        "description": "Callback triggered when clicked by touch, keyboard, or accessibility action."
      },
      {
        "name": "modifier",
        "type": "Modifier",
        "defaultValue": "Modifier",
        "description": "Layout modifier applied to the outer touch-target container."
      },
      {
        "name": "variant",
        "type": "FrogIconButtonVariant",
        "defaultValue": "FrogIconButtonVariant.Filled",
        "description": "Visual semantic variant: Filled, Tonal, Outline, or Ghost."
      },
      {
        "name": "size",
        "type": "FrogIconButtonSize",
        "defaultValue": "FrogIconButtonSize.Medium",
        "description": "Dimensional scale: Small (32dp container), Medium (40dp container), or Large (48dp container)."
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
        "description": "Replaces visible icon with a centered progress indicator while preserving container bounds. Suppresses activation."
      },
      {
        "name": "badge",
        "type": "(@Composable () -> Unit)?",
        "defaultValue": "null",
        "description": "Optional badge overlay slot anchored at TopEnd outside the centered icon."
      },
      {
        "name": "colors",
        "type": "FrogIconButtonColors",
        "defaultValue": "FrogIconButtonDefaults.colors(variant)",
        "description": "Resolved container, content, and border colors across enabled and disabled states."
      },
      {
        "name": "shape",
        "type": "Shape",
        "defaultValue": "FrogIconButtonDefaults.shape(size)",
        "description": "Corner radius shape applied to the button container and border."
      },
      {
        "name": "interactionSource",
        "type": "MutableInteractionSource",
        "defaultValue": "remember { MutableInteractionSource() }",
        "description": "Interaction stream driving press animations and focus rings."
      }
    ],
    "examples": [
      {
        "id": "basic",
        "title": "Basic Action",
        "description": "High-emphasis icon action with a solid container.",
        "codeSnippet": "@Composable\ninternal fun IconButtonBasicExample(modifier: Modifier = Modifier) {\n    FrogIconButton(\n        icon = {\n            Icon(\n                imageVector = FrogIcons.Search,\n                contentDescription = null,\n                modifier = Modifier.size(FrogIconButtonDefaults.iconSize(FrogIconButtonSize.Medium))\n            )\n        },\n        contentDescription = \"Search\",\n        onClick = {},\n        modifier = modifier,\n        variant = FrogIconButtonVariant.Filled\n    )\n}"
      },
      {
        "id": "tonal",
        "title": "Tonal Action",
        "description": "Medium-emphasis icon action on a muted container.",
        "codeSnippet": "@Composable\ninternal fun IconButtonTonalExample(modifier: Modifier = Modifier) {\n    FrogIconButton(\n        icon = {\n            Icon(\n                imageVector = FrogIcons.Settings,\n                contentDescription = null,\n                modifier = Modifier.size(FrogIconButtonDefaults.iconSize(FrogIconButtonSize.Medium))\n            )\n        },\n        contentDescription = \"Settings\",\n        onClick = {},\n        modifier = modifier,\n        variant = FrogIconButtonVariant.Tonal\n    )\n}"
      },
      {
        "id": "outline",
        "title": "Outlined Action",
        "description": "Medium/low-emphasis icon action with a structural border.",
        "codeSnippet": "@Composable\ninternal fun IconButtonOutlineExample(modifier: Modifier = Modifier) {\n    FrogIconButton(\n        icon = {\n            Icon(\n                imageVector = FrogIcons.Close,\n                contentDescription = null,\n                modifier = Modifier.size(FrogIconButtonDefaults.iconSize(FrogIconButtonSize.Medium))\n            )\n        },\n        contentDescription = \"Close\",\n        onClick = {},\n        modifier = modifier,\n        variant = FrogIconButtonVariant.Outline\n    )\n}"
      },
      {
        "id": "ghost",
        "title": "Ghost Action",
        "description": "Lowest-emphasis action for compact toolbars.",
        "codeSnippet": "@Composable\ninternal fun IconButtonGhostExample(modifier: Modifier = Modifier) {\n    FrogIconButton(\n        icon = {\n            Icon(\n                imageVector = FrogIcons.Reset,\n                contentDescription = null,\n                modifier = Modifier.size(FrogIconButtonDefaults.iconSize(FrogIconButtonSize.Medium))\n            )\n        },\n        contentDescription = \"Reset preferences\",\n        onClick = {},\n        modifier = modifier,\n        variant = FrogIconButtonVariant.Ghost\n    )\n}"
      },
      {
        "id": "loading",
        "title": "Loading State",
        "description": "Displays a centered progress indicator and suppresses user interaction.",
        "codeSnippet": "@Composable\ninternal fun IconButtonLoadingExample(modifier: Modifier = Modifier) {\n    FrogIconButton(\n        icon = {\n            Icon(\n                imageVector = FrogIcons.Reset,\n                contentDescription = null,\n                modifier = Modifier.size(FrogIconButtonDefaults.iconSize(FrogIconButtonSize.Medium))\n            )\n        },\n        contentDescription = \"Refreshing content\",\n        onClick = {},\n        modifier = modifier,\n        variant = FrogIconButtonVariant.Filled,\n        loading = true\n    )\n}"
      },
      {
        "id": "badge",
        "title": "Badged Action",
        "description": "Displays an unclipped overlay badge anchored at TopEnd.",
        "codeSnippet": "@Composable\ninternal fun IconButtonBadgeExample(modifier: Modifier = Modifier) {\n    FrogIconButton(\n        icon = {\n            Icon(\n                imageVector = FrogIcons.Info,\n                contentDescription = null,\n                modifier = Modifier.size(FrogIconButtonDefaults.iconSize(FrogIconButtonSize.Medium))\n            )\n        },\n        contentDescription = \"Notifications, 3 unread\",\n        onClick = {},\n        modifier = modifier,\n        variant = FrogIconButtonVariant.Tonal,\n        badge = {\n            Box(\n                modifier = Modifier\n                    .defaultMinSize(minWidth = 16.dp, minHeight = 16.dp)\n                    .background(FrogTheme.colors.destructive, CircleShape)\n                    .padding(horizontal = 4.dp),\n                contentAlignment = Alignment.Center\n            ) {\n                Text(\n                    text = \"3\",\n                    style = FrogTheme.typography.bodySmall,\n                    color = Color.White\n                )\n            }\n        }\n    )\n}"
      },
      {
        "id": "toolbar",
        "title": "Toolbar Actions",
        "description": "Multiple icon buttons composed inside an app toolbar.",
        "codeSnippet": "@Composable\ninternal fun IconButtonToolbarExample(modifier: Modifier = Modifier) {\n    Row(\n        modifier = modifier\n            .fillMaxWidth()\n            .background(FrogTheme.colors.surfaceElevated, FrogTheme.shapes.md)\n            .padding(horizontal = 8.dp, vertical = 6.dp),\n        verticalAlignment = Alignment.CenterVertically\n    ) {\n        FrogIconButton(\n            icon = { Icon(FrogIcons.Back, null, Modifier.size(FrogIconButtonDefaults.iconSize(FrogIconButtonSize.Small))) },\n            contentDescription = \"Navigate back\",\n            onClick = {},\n            variant = FrogIconButtonVariant.Ghost,\n            size = FrogIconButtonSize.Small\n        )\n        Spacer(Modifier.width(8.dp))\n        Text(\n            text = \"Editor Toolbar\",\n            style = FrogTheme.typography.heading,\n            color = FrogTheme.colors.foreground,\n            modifier = Modifier.weight(1f)\n        )\n        FrogIconButton(\n            icon = { Icon(FrogIcons.Search, null, Modifier.size(FrogIconButtonDefaults.iconSize(FrogIconButtonSize.Small))) },\n            contentDescription = \"Search document\",\n            onClick = {},\n            variant = FrogIconButtonVariant.Ghost,\n            size = FrogIconButtonSize.Small\n        )\n        Spacer(Modifier.width(4.dp))\n        FrogIconButton(\n            icon = { Icon(FrogIcons.Sliders, null, Modifier.size(FrogIconButtonDefaults.iconSize(FrogIconButtonSize.Small))) },\n            contentDescription = \"More options\",\n            onClick = {},\n            variant = FrogIconButtonVariant.Ghost,\n            size = FrogIconButtonSize.Small\n        )\n    }\n}"
      }
    ],
    "tags": [
      "action",
      "icon",
      "iconbutton",
      "badge",
      "toolbar"
    ],
    "accessibility": {
      "role": "Role.Button",
      "minTouchTarget": "48dp",
      "talkBackNotes": "contentDescription is mandatory for actionable icon controls. TalkBack announces the button role, label, loading state, and disabled state while child icons remain decorative."
    },
    "quality": {
      "visualStates": [
        "Default",
        "Pressed",
        "Focused",
        "Disabled",
        "Loading",
        "Badge"
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
      "composePreviews": "app/src/main/java/io/github/codewitheswar/frogui/showcase/components/iconbutton/IconButtonComponentPreviews.kt",
      "unitTests": [
        "frogui-components/src/test/java/io/github/codewitheswar/frogui/components/button/FrogIconButtonTest.kt"
      ],
      "androidTests": [
        "app/src/androidTest/java/io/github/codewitheswar/frogui/showcase/PublicApiContractTest.kt"
      ],
      "webPreview": "docs/src/components/preview/previews/icon-button/IconButtonPreview.tsx"
    },
    "source": "frogui-components/src/main/java/io/github/codewitheswar/frogui/components/button/FrogIconButton.kt",
    "showcase": {
      "route": "components/icon-button",
      "source": "app/src/main/java/io/github/codewitheswar/frogui/showcase/components/iconbutton/IconButtonShowcaseDefinition.kt",
      "screen": "iconButtonShowcaseDefinition"
    },
    "prose": "# Usage guidance\n\nUse `FrogIconButton` for compact, icon-only actions such as searching, closing modals, navigating back, opening settings, or toggling favorites. The visible content is primarily an icon, making accessible naming through `contentDescription` strictly mandatory.\n\nWrap application content in `FrogTheme` from `io.github.codewitheswar.frogui.theme`.\nUse composable icon and badge slots, native `Modifier`, semantic variant/size values, and `FrogIconButtonColors` for customization.\n\n## Compose an icon action\n\nKeep application state with the caller. Supply a descriptive action label that explains what happens when activated, rather than describing what the icon looks like:\n\n```kotlin\n@Composable\nfun SearchAction(onSearch: () -> Unit) {\n    FrogIconButton(\n        icon = {\n            Icon(\n                imageVector = FrogIcons.Search,\n                contentDescription = null\n            )\n        },\n        contentDescription = \"Search items\",\n        onClick = onSearch\n    )\n}\n```\n\n> **Accessibility Rule:** `contentDescription` is mandatory for `FrogIconButton`. Child icon composables passed into the `icon` slot should specify `contentDescription = null` so that assistive technologies announce the action once from the parent button.\n\n### When to use\n\n- The action is compact and contextual (e.g. toolbars, card headers, inline table rows).\n- The icon conveys an unambiguous, familiar concept.\n- The control has a clear, actionable `contentDescription`.\n\n### When not to use\n\n- The action requires explanatory text to be understood. Use `FrogButton` instead.\n- The icon is purely decorative or non-interactive. Use a standard `Icon` with `contentDescription = null`. Do not use `FrogIconButton` for decorative visuals.\n- The icon meaning is ambiguous or unfamiliar to your users.\n\n---\n\n## Semantic variants\n\nFrogIconButton supports four semantic emphasis levels:\n\n- **Filled:** Highest visual emphasis with a solid surface and high-contrast content. Ideal for standalone floating actions or primary tool actions.\n- **Tonal:** Medium visual emphasis on a muted surface container. Great for secondary actions in content cards.\n- **Outline:** Medium/low visual emphasis with a defined structural border and transparent background.\n- **Ghost:** Lowest visual emphasis with a transparent background. Ideal for navigation bars, app bars, and high-density toolbars.\n\n```kotlin\nRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {\n    FrogIconButton(\n        icon = { Icon(FrogIcons.Search, null) },\n        contentDescription = \"Search\",\n        onClick = { },\n        variant = FrogIconButtonVariant.Filled\n    )\n    FrogIconButton(\n        icon = { Icon(FrogIcons.Settings, null) },\n        contentDescription = \"Settings\",\n        onClick = { },\n        variant = FrogIconButtonVariant.Tonal\n    )\n    FrogIconButton(\n        icon = { Icon(FrogIcons.Close, null) },\n        contentDescription = \"Close\",\n        onClick = { },\n        variant = FrogIconButtonVariant.Outline\n    )\n    FrogIconButton(\n        icon = { Icon(FrogIcons.Reset, null) },\n        contentDescription = \"Reset\",\n        onClick = { },\n        variant = FrogIconButtonVariant.Ghost\n    )\n}\n```\n\n---\n\n## Visual sizing vs. touch targets\n\nVisual container size and interactive touch target size are distinct:\n\n| Size | Visual Container | Recommended Icon Size | Interactive Touch Target |\n| :--- | :--- | :--- | :--- |\n| **Small** | 32dp × 32dp | 16dp | 48dp × 48dp |\n| **Medium** | 40dp × 40dp | 18dp | 48dp × 48dp |\n| **Large** | 48dp × 48dp | 20dp | 48dp × 48dp |\n\nEven for `Small` icon buttons, `FrogIconButton` enforces a minimum 48dp interactive bounds via `FrogTheme.sizing.minimumTouchTarget` so motor accessibility is never compromised.\n\n---\n\n## Badge overlay\n\nSupport optional badges such as unread notification dots or numerical count badges via the `badge` slot:\n\n```kotlin\nFrogIconButton(\n    icon = { Icon(FrogIcons.Info, null) },\n    contentDescription = \"Notifications, 3 unread\",\n    onClick = { },\n    badge = {\n        Box(\n            modifier = Modifier\n                .defaultMinSize(minWidth = 16.dp, minHeight = 16.dp)\n                .background(FrogTheme.colors.destructive, CircleShape)\n                .padding(horizontal = 4.dp),\n            contentAlignment = Alignment.Center\n        ) {\n            Text(\"3\", style = FrogTheme.typography.bodySmall, color = Color.White)\n        }\n    }\n)\n```\n\n- Badges are placed at `Alignment.TopEnd` relative to the visual container.\n- Badges are treated as overlays and do not alter the button's layout dimensions or minimum touch target.\n- If a badge conveys critical state (e.g. count), enrich the button's `contentDescription` accordingly.\n\n---\n\n## Loading state\n\nWhen `loading = true`:\n\n- The icon is replaced by a centered progress indicator.\n- Button bounds and container dimensions remain perfectly stable (no layout shifts).\n- Interaction is blocked; clicks will not fire.\n- Screen readers receive the loading state without duplicate announcements.\n\n```kotlin\nFrogIconButton(\n    icon = { Icon(FrogIcons.Reset, null) },\n    contentDescription = \"Syncing data\",\n    onClick = { },\n    loading = isSyncing\n)\n```\n\n---\n\n## Accessibility checklist\n\n1. **Mandatory label:** `contentDescription` is required. Avoid generic labels like \"Button\" or image descriptions like \"Magnifying glass\". Describe the action: `\"Search\"`, `\"Close window\"`, `\"Add to cart\"`.\n2. **Decorative child icons:** Ensure icon vectors within the slot pass `contentDescription = null` to prevent duplicate announcements.\n3. **Minimum touch target:** All sizes retain a minimum 48dp target.\n4. **Focus ring:** Keyboard and D-pad navigation display a visible focus ring using `FrogTheme.colors.focusRing`.\n5. **RTL support:** Badges and layout positions respect layout direction via `Start`/`End` semantics.\n"
  },
  {
    "id": "text-field",
    "name": "FrogTextField",
    "displayName": "Text Field",
    "description": "A state-hoisted text input with filled, outline, and underline presentations, supporting content, slots, and accessible error handling.",
    "category": "inputs",
    "status": "experimental",
    "since": "0.1.0-SNAPSHOT",
    "path": "/FrogUI/components/text-field",
    "variants": [
      "Filled",
      "Outline",
      "Underline"
    ],
    "sizes": [],
    "properties": [
      {
        "name": "value",
        "type": "String",
        "defaultValue": "required",
        "description": "Current text value owned by the caller."
      },
      {
        "name": "onValueChange",
        "type": "(String) -> Unit",
        "defaultValue": "required",
        "description": "Callback invoked when proposed text modifications occur."
      },
      {
        "name": "modifier",
        "type": "Modifier",
        "defaultValue": "Modifier",
        "description": "Applied to the outer field container layout."
      },
      {
        "name": "label",
        "type": "String?",
        "defaultValue": "null",
        "description": "Primary identifying text label associated with the field."
      },
      {
        "name": "placeholder",
        "type": "String?",
        "defaultValue": "null",
        "description": "Secondary contextual hint displayed when value is empty."
      },
      {
        "name": "helperText",
        "type": "String?",
        "defaultValue": "null",
        "description": "Non-error supporting information shown below the field when errorText is null."
      },
      {
        "name": "errorText",
        "type": "String?",
        "defaultValue": "null",
        "description": "Validation error message shown below the field; activates error styling and semantics."
      },
      {
        "name": "leading",
        "type": "(@Composable () -> Unit)?",
        "defaultValue": "null",
        "description": "Composable slot rendered at the start of the field."
      },
      {
        "name": "trailing",
        "type": "(@Composable () -> Unit)?",
        "defaultValue": "null",
        "description": "Composable slot rendered at the end of the field."
      },
      {
        "name": "variant",
        "type": "FrogTextFieldVariant",
        "defaultValue": "FrogTextFieldVariant.Filled",
        "description": "Visual presentation style: Filled, Outline, or Underline."
      },
      {
        "name": "enabled",
        "type": "Boolean",
        "defaultValue": "true",
        "description": "Controls interactive capability. When false, user editing is suppressed."
      },
      {
        "name": "readOnly",
        "type": "Boolean",
        "defaultValue": "false",
        "description": "When true, text cannot be edited but remains focusable and selectable."
      },
      {
        "name": "singleLine",
        "type": "Boolean",
        "defaultValue": "false",
        "description": "When true, the field is constrained to a single horizontally scrolling line."
      },
      {
        "name": "maxLines",
        "type": "Int",
        "defaultValue": "if (singleLine) 1 else Int.MAX_VALUE",
        "description": "Maximum visual lines of text allowed."
      },
      {
        "name": "keyboardOptions",
        "type": "KeyboardOptions",
        "defaultValue": "KeyboardOptions.Default",
        "description": "Software keyboard configuration."
      },
      {
        "name": "keyboardActions",
        "type": "KeyboardActions",
        "defaultValue": "KeyboardActions.Default",
        "description": "Callbacks invoked on IME action triggers."
      },
      {
        "name": "visualTransformation",
        "type": "VisualTransformation",
        "defaultValue": "VisualTransformation.None",
        "description": "Formats the visual presentation of the text."
      },
      {
        "name": "interactionSource",
        "type": "MutableInteractionSource?",
        "defaultValue": "null",
        "description": "Hoisted MutableInteractionSource to observe focus and interaction events."
      },
      {
        "name": "colors",
        "type": "FrogTextFieldColors",
        "defaultValue": "FrogTextFieldDefaults.colors(variant)",
        "description": "Color specification mapped to current FrogTheme tokens."
      },
      {
        "name": "shape",
        "type": "Shape",
        "defaultValue": "FrogTextFieldDefaults.shape(variant)",
        "description": "Geometry for the field container border and background."
      }
    ],
    "examples": [
      {
        "id": "basic",
        "title": "Basic Text Field",
        "description": "Label and state-hoisted value entry.",
        "codeSnippet": "var name by rememberSaveable { mutableStateOf(\"\") }\n\nFrogTextField(\n    value = name,\n    onValueChange = { name = it },\n    label = \"Full name\",\n    modifier = Modifier.fillMaxWidth(),\n)"
      },
      {
        "id": "placeholder-helper",
        "title": "Placeholder and Helper Text",
        "description": "Contextual input guidance with helper text.",
        "codeSnippet": "var username by rememberSaveable { mutableStateOf(\"\") }\n\nFrogTextField(\n    value = username,\n    onValueChange = { username = it },\n    label = \"Username\",\n    placeholder = \"e.g. alex_dev\",\n    helperText = \"Only lowercase letters, numbers, and underscores\",\n    modifier = Modifier.fillMaxWidth(),\n)"
      },
      {
        "id": "validation-error",
        "title": "Validation Error",
        "description": "Caller-owned error state and message replacement.",
        "codeSnippet": "var email by rememberSaveable { mutableStateOf(\"invalid-email\") }\nval isValid = email.contains(\"@\") && email.contains(\".\")\n\nFrogTextField(\n    value = email,\n    onValueChange = { email = it },\n    label = \"Email address\",\n    errorText = if (!isValid) \"Enter a valid email address\" else null,\n    helperText = \"We will send your login link here\",\n    modifier = Modifier.fillMaxWidth(),\n)"
      },
      {
        "id": "leading-trailing",
        "title": "Leading and Trailing Content",
        "description": "Icon slots and interactive trailing clear action.",
        "codeSnippet": "var query by rememberSaveable { mutableStateOf(\"Design tokens\") }\n\nFrogTextField(\n    value = query,\n    onValueChange = { query = it },\n    label = \"Search components\",\n    leading = {\n        Icon(\n            imageVector = FrogIcons.Search,\n            contentDescription = null,\n            modifier = Modifier.size(20.dp),\n        )\n    },\n    trailing = if (query.isNotEmpty()) {\n        {\n            FrogIconButton(\n                icon = {\n                    Icon(\n                        imageVector = FrogIcons.Close,\n                        contentDescription = null,\n                        modifier = Modifier.size(FrogIconButtonDefaults.iconSize(FrogIconButtonSize.Small)),\n                    )\n                },\n                contentDescription = \"Clear search\",\n                onClick = { query = \"\" },\n                variant = FrogIconButtonVariant.Ghost,\n                size = FrogIconButtonSize.Small,\n            )\n        }\n    } else null,\n    modifier = Modifier.fillMaxWidth(),\n)"
      },
      {
        "id": "read-only",
        "title": "Read-Only Field",
        "description": "Readable non-editable text field presentation.",
        "codeSnippet": "FrogTextField(\n    value = \"org_2026_production\",\n    onValueChange = {},\n    label = \"Organization identifier\",\n    readOnly = true,\n    helperText = \"Generated during account setup; cannot be modified\",\n    variant = FrogTextFieldVariant.Outline,\n    modifier = Modifier.fillMaxWidth(),\n)"
      },
      {
        "id": "multiline",
        "title": "Multiline Input",
        "description": "Text field expanding up to multiple lines.",
        "codeSnippet": "var notes by rememberSaveable {\n    mutableStateOf(\"Release notes draft:\\n- Added FrogTextField\\n- Verified accessibility\")\n}\n\nFrogTextField(\n    value = notes,\n    onValueChange = { notes = it },\n    label = \"Release notes\",\n    singleLine = false,\n    maxLines = 4,\n    modifier = Modifier.fillMaxWidth(),\n)"
      },
      {
        "id": "form-flow",
        "title": "Form Flow with IME Actions",
        "description": "Keyboard navigation flow across multiple text fields.",
        "codeSnippet": "val focusManager = LocalFocusManager.current\nvar firstName by rememberSaveable { mutableStateOf(\"\") }\nvar email by rememberSaveable { mutableStateOf(\"\") }\n\nColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {\n    FrogTextField(\n        value = firstName,\n        onValueChange = { firstName = it },\n        label = \"First name\",\n        keyboardOptions = KeyboardOptions(\n            keyboardType = KeyboardType.Text,\n            imeAction = ImeAction.Next,\n        ),\n        keyboardActions = KeyboardActions(\n            onNext = { focusManager.moveFocus(FocusDirection.Down) }\n        ),\n        modifier = Modifier.fillMaxWidth(),\n    )\n\n    FrogTextField(\n        value = email,\n        onValueChange = { email = it },\n        label = \"Email\",\n        keyboardOptions = KeyboardOptions(\n            keyboardType = KeyboardType.Email,\n            imeAction = ImeAction.Done,\n        ),\n        keyboardActions = KeyboardActions(\n            onDone = { focusManager.clearFocus() }\n        ),\n        modifier = Modifier.fillMaxWidth(),\n    )\n}"
      }
    ],
    "tags": [
      "input",
      "form",
      "text-field",
      "entry",
      "editable"
    ],
    "accessibility": {
      "role": "TextField",
      "minTouchTarget": "56dp",
      "talkBackNotes": "Persistent or floating label association ensures TalkBack identifies the field after text entry. Error messages are semantically exposed via error semantics. Enabled and read-only states are differentiated."
    },
    "quality": {
      "visualStates": [
        "Default",
        "Focused",
        "Error",
        "Disabled",
        "ReadOnly"
      ],
      "interactions": [
        "Typing",
        "Focus",
        "Clear",
        "IME action",
        "Click"
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
      "composePreviews": "app/src/main/java/io/github/codewitheswar/frogui/showcase/components/textfield/TextFieldComponentPreviews.kt",
      "unitTests": [
        "frogui-components/src/test/java/io/github/codewitheswar/frogui/components/textfield/FrogTextFieldTest.kt"
      ],
      "androidTests": [
        "app/src/androidTest/java/io/github/codewitheswar/frogui/showcase/PublicApiContractTest.kt",
        "app/src/androidTest/java/io/github/codewitheswar/frogui/showcase/TextFieldDetailTest.kt"
      ],
      "webPreview": "docs/src/components/preview/previews/text-field/TextFieldPreview.tsx"
    },
    "source": "frogui-components/src/main/java/io/github/codewitheswar/frogui/components/textfield/FrogTextField.kt",
    "showcase": {
      "route": "components/text-field",
      "source": "app/src/main/java/io/github/codewitheswar/frogui/showcase/components/textfield/TextFieldShowcaseDefinition.kt",
      "screen": "textFieldShowcaseDefinition"
    },
    "prose": "# Usage guidance\n\nUse `FrogTextField` as the canonical general-purpose editable text input for FrogUI forms, dialogs, and text entry screens. Typical use cases include name, email, titles, search queries, usernames, short notes, IDs, and free-form input.\n\nWrap application content in `FrogTheme` from `io.github.codewitheswar.frogui.theme`.\nThe field preserves Compose state hoisting: the application owns `value` and `onValueChange`, while `FrogTextField` handles presentation, interaction, and accessibility.\n\n## Compose a text field\n\nKeep state management with the caller. Declare a mutable state and bind it directly to the field:\n\n```kotlin\n@Composable\nfun UserEmailField() {\n    var email by rememberSaveable { mutableStateOf(\"\") }\n\n    FrogTextField(\n        value = email,\n        onValueChange = { email = it },\n        label = \"Work email\",\n        placeholder = \"name@company.com\",\n        helperText = \"We will send your verification link here\",\n        modifier = Modifier.fillMaxWidth()\n    )\n}\n```\n\n> **Accessibility Rule:** A placeholder is not a label. Important fields must provide a persistent or floating `label` so that users and assistive technologies can identify the field at all times, even after typing begins.\n\n### When to use\n\n- The user needs to enter or edit single-line or multiline text.\n- A visible label identifies the input field.\n- Form validation may apply with contextual helper or error feedback.\n- Configurable software keyboard options (IME) and action buttons are required.\n- Standard accessible editable text semantics are needed.\n\n### When not to use\n\n- Obscured password entry. Use a dedicated password component when available.\n- PIN or numeric OTP codes with distinct digit boxes.\n- Large document-style rich text editing.\n- Discrete choices (checkboxes, radio buttons, switches, or dropdown selectors).\n- Continuous numeric ranges (use sliders or steppers).\n\n---\n\n## Visual variants\n\n`FrogTextField` supports three semantic visual presentations:\n\n| Variant | Container style | Best suited for |\n|---|---|---|\n| **Filled** | Subtle container surface with bottom indicator | Strong field grouping, dense forms, or high visual hierarchy |\n| **Outline** | Explicit surrounding border with transparent background | Clear individual field boundaries across varied surface backgrounds |\n| **Underline** | Minimal surface with bottom-edge border accent | Clean, lightweight interfaces with established structural rhythm |\n\n```kotlin\n// Outline variant\nFrogTextField(\n    value = name,\n    onValueChange = { name = it },\n    label = \"Full name\",\n    variant = FrogTextFieldVariant.Outline\n)\n\n// Underline variant\nFrogTextField(\n    value = title,\n    onValueChange = { title = it },\n    label = \"Project title\",\n    variant = FrogTextFieldVariant.Underline\n)\n```\n\nAll three variants share identical semantic behavior for typing, selection, focus, keyboard handling, labels, placeholders, supporting text, and accessibility.\n\n---\n\n## Label vs placeholder\n\n- **`label`** identifies the field. It floats to a compact top position when the field is focused or contains text, remaining continuously readable.\n- **`placeholder`** displays an example or hint (e.g. `name@example.com`). It appears only when `value` is empty and does not replace the label.\n\n```kotlin\nFrogTextField(\n    value = username,\n    onValueChange = { username = it },\n    label = \"Username\",                     // Identifies the field\n    placeholder = \"e.g. alex_dev\",          // Hint when empty\n    helperText = \"Lowercase letters only\"   // Guidance\n)\n```\n\n---\n\n## Supporting text & error handling\n\nSupporting text renders below the field container with aligned geometry:\n\n- **`helperText`** provides persistent formatting hints or guidance.\n- **`errorText`** provides the authoritative validation error message.\n\n> **Precedence Rule:** When `errorText != null`, the error message replaces `helperText`. Stacking competing messages is avoided.\n\n```kotlin\nvar email by rememberSaveable { mutableStateOf(\"invalid-email\") }\nval isValid = email.contains(\"@\") && email.contains(\".\")\n\nFrogTextField(\n    value = email,\n    onValueChange = { email = it },\n    label = \"Email address\",\n    errorText = if (!isValid) \"Enter a valid email address\" else null,\n    helperText = \"We will send your login link here\"\n)\n```\n\n### Error accessibility\n\nErrors are never communicated by color alone. When `errorText` is present:\n1. The border or underline transitions to `FrogTheme.colors.error`.\n2. The error message is rendered in error-toned supporting text.\n3. The field exposes `SemanticsProperties.Error`, ensuring screen readers announce the validation failure when navigating to or interacting with the field without repetitive announcement spam.\n\n---\n\n## Enabled vs read-only\n\n`enabled = false` and `readOnly = true` represent fundamentally different states:\n\n| State | Editable | Focusable / Selectable | Visual presentation |\n|---|---|---|---|\n| **Enabled** | Yes | Yes | High contrast, interactive affordance |\n| **Read-Only** | No | Yes | Normal readable contrast, non-interactive affordance |\n| **Disabled** | No | No | Muted contrast, clicks and focus suppressed |\n\n```kotlin\n// Read-only: value is visible and selectable, but cannot be changed\nFrogTextField(\n    value = \"org_2026_prod\",\n    onValueChange = {},\n    label = \"Organization ID\",\n    readOnly = true,\n    helperText = \"Generated at workspace creation; cannot be edited\"\n)\n\n// Disabled: field is completely inactive\nFrogTextField(\n    value = \"Legacy plan\",\n    onValueChange = {},\n    label = \"Subscription tier\",\n    enabled = false\n)\n```\n\n---\n\n## Leading & trailing content\n\nUse generic composable slots to compose leading indicators or trailing actions:\n\n```kotlin\nFrogTextField(\n    value = search,\n    onValueChange = { search = it },\n    label = \"Search\",\n    leading = {\n        Icon(FrogIcons.Search, contentDescription = null)\n    },\n    trailing = if (search.isNotEmpty()) {\n        {\n            FrogIconButton(\n                icon = { Icon(FrogIcons.Close, contentDescription = null) },\n                contentDescription = \"Clear search\",\n                onClick = { search = \"\" },\n                variant = FrogIconButtonVariant.Ghost,\n                size = FrogIconButtonSize.Small\n            )\n        }\n    } else null\n)\n```\n\n- **Leading icons** should specify `contentDescription = null` when purely decorative so they do not duplicate the field label.\n- **Interactive trailing controls** should use `FrogIconButton` to preserve independent touch targets and semantic actions.\n\n---\n\n## Keyboard configuration & multiline\n\nConfigure software keyboard behavior via Compose `KeyboardOptions` and `KeyboardActions`:\n\n```kotlin\nFrogTextField(\n    value = email,\n    onValueChange = { email = it },\n    label = \"Email\",\n    singleLine = true,\n    keyboardOptions = KeyboardOptions(\n        keyboardType = KeyboardType.Email,\n        imeAction = ImeAction.Next\n    ),\n    keyboardActions = KeyboardActions(\n        onNext = { focusManager.moveFocus(FocusDirection.Down) }\n    )\n)\n```\n\nFor multiline inputs, set `singleLine = false` and specify `maxLines`:\n\n```kotlin\nFrogTextField(\n    value = notes,\n    onValueChange = { notes = it },\n    label = \"Release notes\",\n    singleLine = false,\n    maxLines = 5,\n    modifier = Modifier.fillMaxWidth()\n)\n```\n\n---\n\n## Sizing & touch targets\n\n- Standard fields maintain a minimum container height of **56dp** (`FrogTextFieldDefaults.MinHeight`), providing generous touch targets and comfortable vertical padding for floating labels.\n- Compact layouts can utilize **48dp** (`FrogTextFieldDefaults.CompactMinHeight`), which strictly fulfills the canonical 48dp minimum touch target for motor accessibility.\n- Slot icons default to **20dp** (`FrogTextFieldDefaults.IconSize`).\n\n---\n\n## Reduced motion & dark theme\n\n- When reduced motion is requested (`FrogTheme.motion.isReduced`), label transitions and border color shifts snap immediately without animated tweens.\n- In dark mode, container surfaces and borders utilize deep semantic tokens (`surfaceVariant`, `border`, `borderFocus`) to ensure crisp visual contrast against dark backgrounds.\n"
  }
];

export function getComponentById(id: string): ComponentDocPage | undefined {
  return catalog.find(c => c.id === id);
}

export function getComponentsByCategory(categoryId: string): ComponentDocPage[] {
  return catalog.filter(c => c.category === categoryId);
}
