import React from 'react';

export type HugeIconData = Array<[string, Record<string, string | number>]>;

interface HugeIconProps {
  icon: HugeIconData;
  className?: string;
  size?: number;
}

export const HugeIcon: React.FC<HugeIconProps> = ({
  icon,
  className = 'w-4 h-4',
  size = 24
}) => {
  return (
    <svg
      viewBox={`0 0 ${size} ${size}`}
      fill="none"
      stroke="currentColor"
      strokeWidth={1.5}
      strokeLinecap="round"
      strokeLinejoin="round"
      className={className}
      aria-hidden="true"
    >
      {icon.map(([type, attrs], idx) => {
        if (type === 'path') {
          return <path key={idx} d={attrs.d as string} />;
        }
        if (type === 'circle') {
          return (
            <circle
              key={idx}
              cx={attrs.cx as number}
              cy={attrs.cy as number}
              r={attrs.r as number}
            />
          );
        }
        return null;
      })}
    </svg>
  );
};
