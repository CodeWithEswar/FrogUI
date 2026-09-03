# FrogUI

<p align="center">
  <img src="app/src/main/res/drawable/frogui_mark.xml" width="96" height="96" alt="FrogUI Logo" />
</p>

<p align="center">
  <strong>Composable components for modern Android.</strong><br>
  <em>Beautifully engineered Android components. Open. Customizable. Native.</em>
</p>

<p align="center">
  <a href="#brand-identity">Brand Identity</a> •
  <a href="#features">Features</a> •
  <a href="#quick-start">Quick Start</a> •
  <a href="#architecture">Architecture</a> •
  <a href="#roadmap">Roadmap</a> •
  <a href="#contributing">Contributing</a>
</p>

---

## Brand Identity

FrogUI adopts a **restrained, modern monochrome visual philosophy** tailored specifically for developer tools and production-grade Android applications.

* **Palette**: Strict monochrome foundation (Zinc `#09090B`, `#18181B`, `#27272A`, `#FFFFFF`) with intentional contrast over arbitrary color.
* **Vector Assets**: 100% vector-first with symmetrical cubic Bézier paths. Zero bitmap dependencies.
* **Adaptive Icons**: Full support for Android adaptive launcher icons (Circle, Squircle, Rounded Square) with 0px clipping safe-zone compliance.
* **Themed Icons**: Android 13+ Material You dynamic monochrome icon support.
* **Native Splash**: Android 12+ SplashScreen API integration.

---

## Features

* **Own Your UI**: Full control over component source and styling without proprietary lock-in.
* **Zero Mascots**: A minimal geometric software-product mark engineered for serious developer tooling.
* **Accessibility by Default**: Strict TalkBack semantics, `Role.Image` accessibility, scalable typography, and touch target compliance.
* **Edge-to-Edge Native**: Full modern Android edge-to-edge support with `WindowInsets` awareness.
* **Responsive Layouts**: Designed for adaptive screens from 360dp compact phones to expanded tablet multi-column inspector layouts.

---

## Quick Start

### 1. Theme Configuration

Wrap your application in `FrogUITheme`:

```kotlin
import io.github.codewitheswar.frogui.ui.theme.FrogUITheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FrogUITheme {
                MainContent()
            }
        }
    }
}
```

### 2. Using the FrogUI Logo

```kotlin
import io.github.codewitheswar.frogui.ui.branding.FrogUiLogo
import io.github.codewitheswar.frogui.ui.branding.FrogUiLogoVariant

// Canonical brand badge
FrogUiLogo(
    size = 32.dp,
    variant = FrogUiLogoVariant.Auto,
    contentDescription = "FrogUI"
)

// Standalone geometric mark (toolbar / navigation)
FrogUiMark(
    size = 24.dp,
    tint = MaterialTheme.colorScheme.onSurface
)
```

---

## Architecture

```text
FrogUI/
├── app/                        # Interactive Android showcase application
│   └── src/main/
│       ├── java/.../frogui/
│       │   ├── ui/branding/    # Reusable Compose brand components
│       │   ├── ui/theme/       # Monochrome design tokens & Theme
│       │   └── MainActivity.kt # Component showcase & launcher simulator
│       └── res/
│           ├── drawable/       # Production VectorDrawable assets
│           ├── mipmap-anydpi-v26/ # Adaptive & themed icons
│           └── values*/        # Colors, strings, and splash themes
└── gradle/                     # Version catalog and Gradle wrapper
```

---

## Component Roadmap

- [x] **Brand & Vector System** (`frogui_mark`, `frogui_logo`, adaptive & themed icons, splash)
- [x] **Design Tokens** (Zinc monochrome color palette, semantic typography, elevation)
- [x] **Compose Brand Components** (`FrogUiLogo`, `FrogUiMark`, `FrogUiLogoVariant`)
- [ ] **Core Actions** (`FrogButton`, `FrogIconButton`, `FrogButtonGroup`, `FrogToggleButton`)
- [ ] **Inputs** (`FrogTextField`, `FrogCheckbox`, `FrogRadio`, `FrogSwitch`)
- [ ] **Data Display** (`FrogCard`, `FrogBadge`, `FrogAvatar`, `FrogDivider`)
- [ ] **Overlays & Feedback** (`FrogDialog`, `FrogBottomSheet`, `FrogAlert`, `FrogTooltip`)
- [ ] **Navigation** (`FrogTopBar`, `FrogTabs`, `FrogSegmentedControl`, `FrogNavigationRail`)
- [ ] **Component Playground & Interactive Inspector**
- [ ] **GitHub Pages Documentation**

---

## Contributing

Contributions are welcome! Please follow conventional commit guidelines and ensure all Gradle checks pass:

```bash
./gradlew check
./gradlew lintDebug
./gradlew testDebugUnitTest
```

---

## License

Apache License 2.0. See [LICENSE](LICENSE) for details.
