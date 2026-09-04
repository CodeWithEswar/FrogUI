import React, { useState, useEffect } from 'react';
import { Shell } from './components/layout/Shell';
import { HomePage } from './pages/HomePage';
import { ComponentDetailPage } from './pages/ComponentDetailPage';
import { GettingStartedPage } from './pages/GettingStartedPage';
import { FoundationPage } from './pages/FoundationPage';
import { MarkdownGalleryPage } from './pages/MarkdownGalleryPage';
import { TechnologyPage } from './pages/TechnologyPage';
import { NotFoundPage } from './pages/NotFoundPage';
import { catalog, getComponentById } from './generated/catalog';

const BASE_PATH = '/FrogUI';

export const App: React.FC = () => {
  const [currentPath, setCurrentPath] = useState<string>(() => {
    return window.location.pathname;
  });

  useEffect(() => {
    const handlePopState = () => {
      setCurrentPath(window.location.pathname);
    };
    window.addEventListener('popstate', handlePopState);
    return () => window.removeEventListener('popstate', handlePopState);
  }, []);

  const navigate = (path: string) => {
    // Normalise path with BASE_PATH for GitHub Pages
    const targetUrl = path.startsWith(BASE_PATH)
      ? path
      : path === '/'
      ? `${BASE_PATH}/`
      : `${BASE_PATH}${path.startsWith('/') ? path : `/${path}`}`;

    window.history.pushState(null, '', targetUrl);
    setCurrentPath(targetUrl);
    window.scrollTo({ top: 0, behavior: 'instant' });
  };

  // Strip BASE_PATH to get the canonical logical route
  const getLogicalRoute = (path: string): string => {
    let clean = path;
    if (clean.startsWith(BASE_PATH)) {
      clean = clean.slice(BASE_PATH.length);
    }
    if (!clean.startsWith('/')) {
      clean = `/${clean}`;
    }
    return clean.replace(/\/$/, '') || '/';
  };

  const logicalRoute = getLogicalRoute(currentPath);

  const renderContent = () => {
    if (logicalRoute === '/') {
      return <HomePage onNavigate={navigate} />;
    }

    if (logicalRoute.startsWith('/components/')) {
      const componentId = logicalRoute.replace('/components/', '');
      const component = getComponentById(componentId);
      if (component) {
        return <ComponentDetailPage component={component} onNavigate={navigate} />;
      }
      // If component not found, fallback to button or 404
      const defaultButton = catalog.find(c => c.id === 'button');
      if (defaultButton && componentId === '') {
        return <ComponentDetailPage component={defaultButton} onNavigate={navigate} />;
      }
      return <NotFoundPage onNavigate={navigate} />;
    }

    if (logicalRoute === '/docs/introduction') {
      return <GettingStartedPage section="introduction" onNavigate={navigate} />;
    }

    if (logicalRoute === '/docs/installation') {
      return <GettingStartedPage section="installation" onNavigate={navigate} />;
    }

    if (logicalRoute === '/docs/quick-start') {
      return <GettingStartedPage section="quick-start" onNavigate={navigate} />;
    }

    if (logicalRoute === '/docs/technology' || logicalRoute === '/architecture/technology') {
      return <TechnologyPage onNavigate={navigate} />;
    }

    if (logicalRoute.startsWith('/foundation')) {
      return <FoundationPage section={logicalRoute.split('/').at(-1)} />;
    }

    if (logicalRoute === '/dev/markdown') {
      return <MarkdownGalleryPage />;
    }

    return <NotFoundPage onNavigate={navigate} />;
  };

  return (
    <Shell currentPath={currentPath} onNavigate={navigate}>
      {renderContent()}
    </Shell>
  );
};
