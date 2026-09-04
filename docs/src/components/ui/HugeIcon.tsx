import React from 'react';

export type HugeIconData = Array<[string, Record<string, string | number>]>;

interface HugeIconProps extends React.SVGAttributes<SVGSVGElement> {
  icon: HugeIconData;
  className?: string;
  size?: number;
  strokeWidth?: number;
}

export const HugeIcon: React.FC<HugeIconProps> = ({
  icon,
  className = '',
  size = 20,
  strokeWidth = 1.5,
  style,
  ...rest
}) => {
  if (!icon || !Array.isArray(icon)) {
    return null;
  }

  return (
    <svg
      viewBox="0 0 24 24"
      width={size}
      height={size}
      fill="none"
      stroke="currentColor"
      strokeWidth={strokeWidth}
      strokeLinecap="round"
      strokeLinejoin="round"
      className={`shrink-0 inline-block align-middle ${className}`}
      style={{
        width: `${size}px`,
        height: `${size}px`,
        minWidth: `${size}px`,
        minHeight: `${size}px`,
        ...style
      }}
      aria-hidden="true"
      {...rest}
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
