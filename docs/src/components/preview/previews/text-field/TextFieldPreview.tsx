import React, { useState } from 'react';
import { ComponentPreviewProps } from '../../types';
import { PreviewCheckbox } from '../../PreviewCheckbox';

export const TextFieldPreview: React.FC<ComponentPreviewProps> = ({ isDark }) => {
  const [variant, setVariant] = useState<'Filled' | 'Outline' | 'Underline'>('Filled');
  const [value, setValue] = useState('alex@example.com');
  const label = 'Email address';
  const [placeholder] = useState('name@example.com');
  const [helperText] = useState('We will send your sign-in link here');
  const [hasError, setHasError] = useState(false);
  const [errorMessage, setErrorMessage] = useState('Please enter a valid email address');
  const [hasLeading, setHasLeading] = useState(true);
  const [hasTrailing, setHasTrailing] = useState(true);
  const [disabled, setDisabled] = useState(false);
  const [readOnly, setReadOnly] = useState(false);
  const [singleLine, setSingleLine] = useState(true);
  const [isFocused, setIsFocused] = useState(false);

  const isFloated = isFocused || value.length > 0;

  const getContainerStyles = () => {
    let base = 'relative flex items-center transition-all duration-200 w-full ';
    
    if (disabled) {
      base += isDark 
        ? 'bg-zinc-800/40 text-zinc-500 cursor-not-allowed opacity-60 ' 
        : 'bg-zinc-100 text-zinc-400 cursor-not-allowed opacity-60 ';
    } else if (readOnly) {
      base += isDark 
        ? 'bg-zinc-800/20 text-zinc-200 ' 
        : 'bg-zinc-50 text-zinc-800 ';
    }

    if (variant === 'Filled') {
      base += 'rounded-t-lg min-h-[56px] px-3.5 pt-3 pb-1 border-b-2 ';
      if (hasError) {
        base += 'bg-red-500/10 border-red-500 ';
      } else if (isFocused) {
        base += isDark ? 'bg-zinc-800/70 border-zinc-100 ' : 'bg-zinc-200/60 border-zinc-900 ';
      } else {
        base += isDark ? 'bg-zinc-800/50 border-zinc-700 hover:border-zinc-500 ' : 'bg-zinc-100/90 border-zinc-300 hover:border-zinc-400 ';
      }
    } else if (variant === 'Outline') {
      base += 'rounded-xl min-h-[56px] px-3.5 py-2 border ';
      if (hasError) {
        base += 'border-red-500 ring-1 ring-red-500/30 ';
      } else if (isFocused) {
        base += isDark ? 'border-zinc-100 ring-1 ring-zinc-100/30 ' : 'border-zinc-900 ring-1 ring-zinc-900/20 ';
      } else {
        base += isDark ? 'border-zinc-700 hover:border-zinc-500 bg-zinc-900/30 ' : 'border-zinc-300 hover:border-zinc-400 bg-white ';
      }
    } else {
      // Underline
      base += 'min-h-[56px] px-1 pt-3 pb-1 border-b-2 ';
      if (hasError) {
        base += 'border-red-500 ';
      } else if (isFocused) {
        base += isDark ? 'border-zinc-100 ' : 'border-zinc-900 ';
      } else {
        base += isDark ? 'border-zinc-700 hover:border-zinc-500 ' : 'border-zinc-300 hover:border-zinc-400 ';
      }
    }

    return base;
  };

  return (
    <div className="w-full h-full flex flex-col justify-between">
      {/* Header Controls */}
      <div className="flex items-center justify-between gap-2.5 px-3 sm:px-4 py-2.5 border-b border-zinc-200 dark:border-zinc-800 bg-zinc-50/90 dark:bg-zinc-900/90">
        <div className="flex items-center gap-1 shrink-0 text-xs">
          <span className="text-zinc-500 font-medium mr-1 text-[11px] uppercase tracking-wider">
            Variant:
          </span>
          {(['Filled', 'Outline', 'Underline'] as const).map(v => (
            <button
              key={v}
              onClick={() => setVariant(v)}
              className={`px-2.5 py-1 rounded-md text-xs font-medium transition-colors cursor-pointer ${
                variant === v
                  ? 'bg-zinc-900 text-white dark:bg-zinc-100 dark:text-zinc-900 shadow-xs font-semibold'
                  : 'text-zinc-600 dark:text-zinc-400 hover:bg-zinc-200 dark:hover:bg-zinc-800'
              }`}
            >
              {v}
            </button>
          ))}
        </div>

        <div className="flex items-center gap-2">
          <button
            onClick={() => {
              setHasError(!hasError);
              if (!hasError) setErrorMessage('Please enter a valid email address');
            }}
            className={`px-2.5 py-1 rounded-md text-xs font-medium transition-colors cursor-pointer ${
              hasError
                ? 'bg-red-500/20 text-red-600 dark:text-red-400 border border-red-500/40 font-semibold'
                : 'text-zinc-600 dark:text-zinc-400 hover:bg-zinc-200 dark:hover:bg-zinc-800'
            }`}
          >
            {hasError ? 'Error Active' : 'Trigger Error'}
          </button>
        </div>
      </div>

      {/* Main Canvas Area */}
      <div className="flex-1 flex items-center justify-center p-6 sm:p-10">
        <div className="w-full max-w-sm flex flex-col gap-1.5">
          <div className={getContainerStyles()}>
            {/* Leading icon slot */}
            {hasLeading && (
              <div className="mr-2.5 text-zinc-400 dark:text-zinc-500 shrink-0 select-none">
                <svg className="w-5 h-5" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
                  <path d="M4 4h16c1.1 0 2 .9 2 2v12c0 1.1-.9 2-2 2H4c-1.1 0-2-.9-2-2V6c0-1.1.9-2 2-2z" />
                  <polyline points="22,6 12,13 2,6" />
                </svg>
              </div>
            )}

            {/* Input and Floating Label Area */}
            <div className="relative flex-1 flex flex-col justify-center min-w-0">
              {/* Floating label */}
              {label && (
                <label
                  htmlFor="frog-text-field-preview-input"
                  className={`absolute left-0 transition-all duration-150 pointer-events-none select-none ${
                    isFloated
                      ? 'text-[11px] font-medium -top-1 ' +
                        (hasError
                          ? 'text-red-500'
                          : isFocused
                          ? isDark ? 'text-zinc-100' : 'text-zinc-900'
                          : 'text-zinc-500 dark:text-zinc-400')
                      : 'text-sm top-1/2 -translate-y-1/2 text-zinc-400 dark:text-zinc-500'
                  }`}
                >
                  {label}
                </label>
              )}

              {/* Native interactive input / textarea */}
              {singleLine ? (
                <input
                  id="frog-text-field-preview-input"
                  type="text"
                  value={value}
                  onChange={e => setValue(e.target.value)}
                  onFocus={() => setIsFocused(true)}
                  onBlur={() => setIsFocused(false)}
                  disabled={disabled}
                  readOnly={readOnly}
                  placeholder={isFloated ? placeholder : ''}
                  aria-invalid={hasError}
                  aria-describedby="frog-text-field-preview-supporting"
                  className={`w-full bg-transparent border-0 outline-none text-sm p-0 transition-colors ${
                    isFloated ? 'pt-3' : ''
                  } ${
                    isDark ? 'text-zinc-100 placeholder-zinc-600' : 'text-zinc-900 placeholder-zinc-400'
                  } ${disabled ? 'cursor-not-allowed' : ''}`}
                />
              ) : (
                <textarea
                  id="frog-text-field-preview-input"
                  value={value}
                  onChange={e => setValue(e.target.value)}
                  onFocus={() => setIsFocused(true)}
                  onBlur={() => setIsFocused(false)}
                  disabled={disabled}
                  readOnly={readOnly}
                  rows={3}
                  placeholder={isFloated ? placeholder : ''}
                  aria-invalid={hasError}
                  aria-describedby="frog-text-field-preview-supporting"
                  className={`w-full bg-transparent border-0 outline-none text-sm p-0 resize-none transition-colors ${
                    isFloated ? 'pt-3' : ''
                  } ${
                    isDark ? 'text-zinc-100 placeholder-zinc-600' : 'text-zinc-900 placeholder-zinc-400'
                  } ${disabled ? 'cursor-not-allowed' : ''}`}
                />
              )}
            </div>

            {/* Trailing slot: clear button */}
            {hasTrailing && value.length > 0 && !disabled && !readOnly && (
              <button
                type="button"
                onClick={() => setValue('')}
                aria-label="Clear input"
                className="ml-2 text-zinc-400 hover:text-zinc-600 dark:hover:text-zinc-200 p-1 rounded-full transition-colors cursor-pointer shrink-0"
              >
                <svg className="w-4 h-4" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2.5" strokeLinecap="round" strokeLinejoin="round">
                  <line x1="18" y1="6" x2="6" y2="18" />
                  <line x1="6" y1="6" x2="18" y2="18" />
                </svg>
              </button>
            )}
          </div>

          {/* Supporting text area (Helper or Error) */}
          <div
            id="frog-text-field-preview-supporting"
            className={`text-xs px-1 min-h-[18px] transition-colors flex items-center gap-1 ${
              hasError
                ? 'text-red-500 font-medium'
                : 'text-zinc-500 dark:text-zinc-400'
            }`}
          >
            {hasError && (
              <svg className="w-3.5 h-3.5 shrink-0" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                <circle cx="12" cy="12" r="10" />
                <line x1="12" y1="8" x2="12" y2="12" />
                <line x1="12" y1="16" x2="12.01" y2="16" />
              </svg>
            )}
            <span>{hasError ? errorMessage : helperText}</span>
          </div>
        </div>
      </div>

      {/* Footer Controls */}
      <div className="flex flex-wrap items-center justify-between gap-3 px-4 py-2.5 border-t border-zinc-200 dark:border-zinc-800 bg-zinc-50/90 dark:bg-zinc-900/90 text-xs">
        <div className="flex flex-wrap items-center gap-4">
          <PreviewCheckbox
            label="Single line"
            checked={singleLine}
            onChange={setSingleLine}
            isDark={isDark}
          />
          <PreviewCheckbox
            label="Leading icon"
            checked={hasLeading}
            onChange={setHasLeading}
            isDark={isDark}
          />
          <PreviewCheckbox
            label="Trailing action"
            checked={hasTrailing}
            onChange={setHasTrailing}
            isDark={isDark}
          />
          <PreviewCheckbox
            label="Read only"
            checked={readOnly}
            onChange={setReadOnly}
            isDark={isDark}
          />
          <PreviewCheckbox
            label="Disabled"
            checked={disabled}
            onChange={setDisabled}
            isDark={isDark}
          />
        </div>

        <div className="text-[11px] text-zinc-400">
          56dp touch height
        </div>
      </div>
    </div>
  );
};
