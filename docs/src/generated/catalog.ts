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
        "codeSnippet": "@Composable\ninternal fun ButtonLeadingExample(modifier: Modifier = Modifier) {\n    FrogButton(onClick = {}, modifier = modifier, leadingIcon = { Icon(FrogIcons.Play, null, Modifier.size(18.dp)) }) { Text(\"Run preview\") }\n}"
      },
      {
        "id": "trailing",
        "title": "Trailing icon",
        "description": "Indicate progression with a decorative trailing icon.",
        "codeSnippet": "@Composable\ninternal fun ButtonTrailingExample(modifier: Modifier = Modifier) {\n    FrogButton(onClick = {}, modifier = modifier, trailingIcon = { Icon(FrogIcons.Forward, null, Modifier.size(18.dp)) }) { Text(\"Continue\") }\n}"
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
    "source": "frogui-components/src/main/java/io/github/codewitheswar/frogui/components/button/FrogButton.kt",
    "showcase": {
      "route": "components/button",
      "source": "app/src/main/java/io/github/codewitheswar/frogui/showcase/components/button/ButtonScreen.kt",
      "screen": "ButtonScreen"
    },
    "prose": "# Usage guidance\n\nUse Button to trigger an action such as saving a form or continuing a workflow.\nThe caller supplies the action callback and owns enabled/loading state. Loading\nsuppresses activation; it does not start network work or retain application state.\n\nWrap application content in `FrogTheme` from `io.github.codewitheswar.frogui.theme`.\nUse composable content and icon slots, native Modifier, semantic variant/size values,\nand FrogButtonColors for customization. API signatures, examples, capabilities, and\nstatus come from the generated registry rather than this prose.\n\n## Compose an action\n\nKeep application state with the caller. Pass a callback to the button and use its\ncontent slot for the visible label.\n\n```kotlin\n@Composable\nfun ContinueAction(enabled: Boolean, onContinue: () -> Unit) {\n    FrogButton(enabled = enabled, onClick = onContinue) {\n        Text(\"Continue\")\n    }\n}\n```\n\n### Choose a variant\n\n- **Primary** gives the main action the most emphasis.\n- **Secondary**, **Outline**, and **Ghost** support quieter actions.\n- **Destructive** identifies an action that needs destructive intent.\n\n> Loading blocks activation. The caller remains responsible for starting work,\n> handling its result, and updating `loading`.\n\n## Customize colors and layout\n\nUse `fullWidth = true` to expand both the visible surface and its touch target.\nSmall, Medium, and Large use one shared size model for padding, icon size, spacing,\nand minimum surface height. Prefer semantic shapes; override `shape` only when\nthe surrounding design calls for it.\n\n```kotlin\nFrogButton(\n    variant = FrogButtonVariant.Outline,\n    fullWidth = true,\n    colors = FrogButtonDefaults.colors(\n        variant = FrogButtonVariant.Outline,\n        containerColor = FrogTheme.colors.surfaceElevated,\n        contentColor = FrogTheme.colors.foreground\n    ),\n    onClick = { /* Continue */ }\n) {\n    Text(\"Continue\")\n}\n```\n\nThe color defaults accept selected overrides, including border and disabled\ncolors. The default border uses the supplied `FrogButtonColors`. Keep the same\nvariant in the button and its color defaults. Semantic tokens follow the theme;\na custom literal such as `Color(0x8018181B)` keeps its ARGB value and alpha.\n\n## Use the component laboratory\n\n- Quick controls change variant and size immediately.\n- **Customize** opens grouped appearance, content, state, and color controls.\n- Wider layouts keep the inspector beside the preview.\n- Color rows open the same drawer in either theme-token or custom mode.\n- Drag the saturation/brightness plane, use the accessible sliders, or enter\n  `#RRGGBB` / `#AARRGGBB` (alpha first). Invalid input disables Apply.\n- A color draft updates the miniature button and live preview. **Apply** commits;\n  **Cancel**, Back, the close action, and outside dismissal leave committed colors\n  unchanged. Back returns to the parent inspector when opened from Customize.\n- **Reset** in a color editor drafts the current variant default. **Reset colors**\n  restores all colors without changing size, label, enabled, or loading state.\n- Changing variant restores its defaults. Generated Kotlin updates immediately,\n  preserving semantic token expressions.\n- Preview theme, width, background, and alignment are independent of exported code.\n- Tap an API property for its type, default, guidance, values, and a copyable example.\n\n## Accessibility\n\n- **Role and target:** one Button action with a minimum 48dp touch target. Surface\n  dimensions may be smaller for Small and Medium.\n- **Enabled:** disabling suppresses activation and exposes disabled semantics.\n- **Loading:** the visible label and icon slots are replaced by a centered spinner,\n  while retaining their measured bounds and accessible label. The state is Loading;\n  the spinner itself does not repeatedly announce progress. Activation is blocked.\n- **Focus:** keyboard focus draws a visible semantic focus ring. Use a shared\n  `MutableInteractionSource` when observing interactions.\n- **Label and icons:** use clear action text; decorative slots have a null content\n  description. Do not nest clickable controls inside the content.\n- **Contrast:** custom colors may reduce readability. The picker composites alpha\n  over the chosen opaque preview canvas, then calculates relative-luminance text\n  contrast. Its 4.5:1 normal-text feedback is informational, not a certification.\n  Transparent backdrops can change the result; inactive controls are exempt from\n  the minimum text contrast requirement. See the\n  [W3C contrast explanation](https://www.w3.org/WAI/WCAG22/Understanding/contrast-minimum.html).\n- **Text scaling:** content can grow vertically; inspector choices scroll rather\n  than truncate. Sliders expose labels, values, and adjustment actions.\n- **Motion:** the showcase respects its reduced-motion preference and the system\n  animator setting. Consumer apps can provide zero-duration `FrogMotion` tokens.\n\nAutomated semantics and interaction tests provide focused evidence. Human TalkBack\nspeech/traversal, physical tablet input, and minimum-API review remain release checks\nbefore this experimental component can be considered Stable.\n\n## Native preview\n\nUse the Android Showcase for real interaction. Web documentation presents metadata,\nusage code, and documentation; it does not execute native Compose.\r\n"
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
    "sizes": [
      "Compact",
      "Expanded"
    ],
    "properties": [
      {
        "name": "state",
        "type": "FrogDrawerState",
        "defaultValue": "rememberFrogDrawerState()",
        "description": "Controls drawer visibility, current value, target value, and programmatic open/close suspend operations."
      },
      {
        "name": "onDismissRequest",
        "type": "() -> Unit",
        "defaultValue": "required",
        "description": "Callback invoked when the drawer is dismissed via scrim tap, back press, or drag-to-dismiss gesture."
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
        "description": "Presentation mode: Auto adapts to screen width (<620dp bottom, >=620dp side), Bottom presents as bottom sheet, Side presents as docked edge panel."
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
      "role": "Role.Dialog",
      "minTouchTarget": "48dp",
      "talkBackNotes": "Declares paneTitle semantics for window transition announcement, trapped focus, and provides accessible 48dp dismiss targets and back navigation."
    },
    "source": "frogui-components/src/main/java/io/github/codewitheswar/frogui/components/overlays/drawer/FrogDrawer.kt",
    "showcase": {
      "route": "components/drawer",
      "source": "app/src/main/java/io/github/codewitheswar/frogui/showcase/components/drawer/DrawerScreen.kt",
      "screen": "DrawerScreen"
    },
    "prose": "# Usage guidance\n\nUse Drawer to present contextual content, secondary workflows, and property inspectors without navigating away from the current screen destination.\nThe caller owns the drawer state and supplies the dismiss callback. Dismiss gestures include modal backdrop taps, system back gestures, and downward drag gestures on compact bottom presentations.\n\nWrap application content in `FrogTheme` from `io.github.codewitheswar.frogui.theme`.\nUse composable content, header, preview, and sticky footer slots, adaptive presentation modes, and `FrogDrawerColors` for customization. API signatures, examples, capabilities, and status come directly from the generated registry.\n\n## Compose a drawer\n\nHoisting state allows external triggers (such as toolbar buttons or menu items) to imperatively launch and dismiss the drawer.\n\n```kotlin\n@Composable\nfun SettingsDrawer() {\n    val drawerState = rememberFrogDrawerState()\n    val scope = rememberCoroutineScope()\n\n    FrogButton(\n        onClick = { scope.launch { drawerState.open() } }\n    ) {\n        Text(\"Configure Settings\")\n    }\n\n    FrogDrawer(\n        state = drawerState,\n        onDismissRequest = { scope.launch { drawerState.close() } },\n        title = \"Settings\",\n        subtitle = \"Manage application preferences\"\n    ) {\n        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {\n            Text(\"General Preferences\", style = FrogTheme.typography.heading)\n            Text(\"Notification and appearance settings.\")\n        }\n    }\n}\n```\n\n### Choose an adaptive presentation\n\n- **Auto** (default) dynamically presents as a modal bottom sheet on compact mobile screens (< 620dp) and docks as a contextual side panel on tablet and desktop surfaces (>= 620dp).\n- **Bottom** forces bottom sheet presentation with top rounded corners and an interactive drag-to-dismiss handle indicator.\n- **Side** docks to a specified screen edge (`FrogDrawerSide.End` or `FrogDrawerSide.Start`) as a fixed-width inspector surface.\n\n> On bottom presentation, downward drags exceeding the 64dp velocity threshold trigger dismissal automatically.\n\n## Customize slots and layout\n\nFrogDrawer provides dedicated structural slots:\n- **Header**: Includes title, subtitle, optional `navigationIcon` (e.g. back navigation), and optional trailing `actions`.\n- **Preview**: Rendered immediately below the header divider for live preview canvases or status badges.\n- **Content**: The main scrollable body. Internal scroll state is managed automatically so headers and footers remain pinned.\n- **Footer**: Sticky bottom bar rendered outside the scrollable body, typically housing primary and secondary action buttons.\n\n```kotlin\nFrogDrawer(\n    state = drawerState,\n    onDismissRequest = { scope.launch { drawerState.close() } },\n    title = \"Edit Profile\",\n    footer = {\n        Row(\n            modifier = Modifier.fillMaxWidth(),\n            horizontalArrangement = Arrangement.spacedBy(8.dp)\n        ) {\n            FrogButton(\n                onClick = { scope.launch { drawerState.close() } },\n                modifier = Modifier.weight(1f),\n                variant = FrogButtonVariant.Secondary\n            ) {\n                Text(\"Cancel\")\n            }\n            FrogButton(\n                onClick = { scope.launch { drawerState.close() } },\n                modifier = Modifier.weight(1f),\n                variant = FrogButtonVariant.Primary\n            ) {\n                Text(\"Save\")\n            }\n        }\n    }\n) {\n    Text(\"User profile form fields and configurations.\")\n}\n```\n\n## Accessibility and Semantics\n\n- **Pane Semantics**: Automatically declares `paneTitle = title ?: \"Drawer\"` so screen readers announce window transitions when opened.\n- **Semantic Dismissal**: Exposes the standard accessibility dismiss action to assistive tools.\n- **Focus Management**: Traps focus within the modal window upon entrance and focuses the primary close control.\n- **Touch Target Compliance**: Close controls and navigation icons strictly satisfy the 48dp minimum accessible touch target requirement.\n"
  }
];

export function getComponentById(id: string): ComponentDocPage | undefined {
  return catalog.find(c => c.id === id);
}

export function getComponentsByCategory(categoryId: string): ComponentDocPage[] {
  return catalog.filter(c => c.category === categoryId);
}
