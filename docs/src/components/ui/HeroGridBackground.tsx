import React from 'react';

export const HeroGridBackground: React.FC = () => {
  return (
    <div
      className="absolute inset-0 -top-6 overflow-hidden pointer-events-none select-none z-0"
      aria-hidden="true"
    >
      {/* Radial vignette mask for smooth edge falloff */}
      <div
        className="absolute inset-0 z-10"
        style={{
          background:
            'radial-gradient(ellipse 70% 60% at 50% 25%, transparent 20%, var(--tw-gradient-to, #09090b) 95%)'
        }}
      />

      {/* Grid Pattern SVG with Animated Moving Indicators */}
      <svg
        className="w-full h-full opacity-70 dark:opacity-50"
        xmlns="http://www.w3.org/2000/svg"
      >
        <defs>
          {/* 48px x 48px Grid Pattern */}
          <pattern
            id="hero-grid-pattern"
            width="48"
            height="48"
            patternUnits="userSpaceOnUse"
          >
            {/* Grid square lines */}
            <path
              d="M 48 0 L 0 0 0 48"
              fill="none"
              stroke="currentColor"
              strokeWidth="1"
              className="text-zinc-200/80 dark:text-zinc-800/80"
            />
            {/* Corner intersection dot */}
            <circle
              cx="0"
              cy="0"
              r="1.5"
              fill="currentColor"
              className="text-zinc-300 dark:text-zinc-700"
            />
          </pattern>

          {/* Glowing gradients for moving beam trails */}
          <linearGradient id="beam-glow-1" x1="0%" y1="0%" x2="100%" y2="0%">
            <stop offset="0%" stopColor="transparent" />
            <stop offset="60%" stopColor="#71717a" stopOpacity="0.4" />
            <stop offset="95%" stopColor="#ffffff" stopOpacity="0.9" />
            <stop offset="100%" stopColor="#ffffff" stopOpacity="1" />
          </linearGradient>

          <linearGradient id="beam-glow-2" x1="0%" y1="0%" x2="0%" y2="100%">
            <stop offset="0%" stopColor="transparent" />
            <stop offset="70%" stopColor="#10b981" stopOpacity="0.3" />
            <stop offset="95%" stopColor="#34d399" stopOpacity="0.8" />
            <stop offset="100%" stopColor="#6ee7b7" stopOpacity="1" />
          </linearGradient>

          {/* Glow filter */}
          <filter id="glow" x="-20%" y="-20%" width="140%" height="140%">
            <feGaussianBlur stdDeviation="3" result="blur" />
            <feComposite in="SourceGraphic" in2="blur" operator="over" />
          </filter>
        </defs>

        {/* Base Grid Fill */}
        <rect width="100%" height="100%" fill="url(#hero-grid-pattern)" />

        {/* Ambient subtle decorative glowing squares on the grid */}
        <rect
          x="144"
          y="96"
          width="48"
          height="48"
          className="fill-zinc-200/50 dark:fill-zinc-800/40 animate-pulse"
          style={{ animationDuration: '4s' }}
        />
        <rect
          x="480"
          y="144"
          width="48"
          height="48"
          className="fill-zinc-200/40 dark:fill-zinc-800/30 animate-pulse"
          style={{ animationDuration: '6s', animationDelay: '1s' }}
        />
        <rect
          x="720"
          y="48"
          width="48"
          height="48"
          className="fill-zinc-200/50 dark:fill-zinc-800/40 animate-pulse"
          style={{ animationDuration: '5s', animationDelay: '2s' }}
        />
        <rect
          x="864"
          y="192"
          width="48"
          height="48"
          className="fill-zinc-200/40 dark:fill-zinc-800/30 animate-pulse"
          style={{ animationDuration: '7s', animationDelay: '0.5s' }}
        />

        {/* ========================================================================= */}
        {/* Animated Moving Indicator 1 - Horizontal/Vertical Grid Circuit */}
        {/* ========================================================================= */}
        <g>
          {/* Movement circuit path */}
          <path
            id="circuit-path-1"
            d="M 48 96 H 432 V 192 H 192 V 288 H 624 V 96 H 816"
            fill="none"
            stroke="url(#beam-glow-1)"
            strokeWidth="2"
            strokeDasharray="96 800"
            strokeLinecap="round"
          >
            <animate
              attributeName="stroke-dashoffset"
              from="896"
              to="0"
              dur="8s"
              repeatCount="indefinite"
            />
          </path>

          {/* Moving Indicator Head - Glowing Dot */}
          <circle r="3.5" className="fill-white dark:fill-zinc-100" filter="url(#glow)">
            <animateMotion
              dur="8s"
              repeatCount="indefinite"
              path="M 48 96 H 432 V 192 H 192 V 288 H 624 V 96 H 816"
            />
          </circle>
          {/* Ambient outer halo for indicator */}
          <circle r="8" className="fill-white/30 dark:fill-zinc-300/30">
            <animateMotion
              dur="8s"
              repeatCount="indefinite"
              path="M 48 96 H 432 V 192 H 192 V 288 H 624 V 96 H 816"
            />
          </circle>
        </g>

        {/* ========================================================================= */}
        {/* Animated Moving Indicator 2 - Secondary Ambient Circuit */}
        {/* ========================================================================= */}
        <g>
          <path
            id="circuit-path-2"
            d="M 864 48 V 240 H 672 V 336 H 960"
            fill="none"
            stroke="url(#beam-glow-1)"
            strokeWidth="1.5"
            strokeDasharray="80 600"
            strokeLinecap="round"
          >
            <animate
              attributeName="stroke-dashoffset"
              from="680"
              to="0"
              dur="6s"
              repeatCount="indefinite"
            />
          </path>

          {/* Moving Indicator Head 2 */}
          <circle r="3" className="fill-white dark:fill-zinc-200" filter="url(#glow)">
            <animateMotion
              dur="6s"
              repeatCount="indefinite"
              path="M 864 48 V 240 H 672 V 336 H 960"
            />
          </circle>
        </g>
      </svg>
    </div>
  );
};
