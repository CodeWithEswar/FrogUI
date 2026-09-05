import React, { useState, useEffect } from 'react';
import { Shell } from './components/layout/Shell';
import { HomePage } from './pages/HomePage';
import { ComponentDetailPage } from './pages/ComponentDetailPage';
import { MarkdownGalleryPage } from './pages/MarkdownGalleryPage';
import { DocumentationPage } from './pages/DocumentationPage';
import { NotFoundPage } from './pages/NotFoundPage';
import { catalog, getComponentById } from './generated/catalog';
import { documentationPages, normalizeDocsPath } from './navigation';

const BASE_PATH = '/FrogUI';

const foundationHashRedirects: Record<string, string> = {
  overview: '/foundations',
  colors: '/foundations/colors',
  theme: '/foundations/colors',
  typography: '/foundations/typography',
  spacing: '/foundations/spacing',
  shapes: '/foundations/shapes',
  elevation: '/foundations/elevation',
  motion: '/foundations/motion',
  sizing: '/foundations/sizing',
  adaptive: '/foundations/adaptive',
  accessibility: '/foundations/accessibility'
};

const architectureHashRedirects: Record<string, string> = {
  technology: '/architecture/technology-foundation',
  'technology-foundation': '/architecture/technology-foundation',
  repository: '/architecture/repository',
  'api-design': '/architecture/api-design',
  'component-standard': '/architecture/component-standard',
  registry: '/architecture/registry',
  release: '/architecture/release'
};

export const App: React.FC = () => {
  const [currentPath, setCurrentPath] = useState<string>(() => {
    return window.location.pathname + window.location.hash;
  });

  useEffect(() => {
    const handlePopState = () => {
      setCurrentPath(window.location.pathname + window.location.hash);
    };
    window.addEventListener('popstate', handlePopState);
    return () => window.removeEventListener('popstate', handlePopState);
  }, []);

  const navigate = (path: string) => {
    const [requestedPath, hash = ''] = path.split('#');
    // Normalise path with BASE_PATH for GitHub Pages
    const targetPath = requestedPath.startsWith(BASE_PATH)
      ? requestedPath
      : requestedPath === '/'
      ? `${BASE_PATH}/`
      : `${BASE_PATH}${requestedPath.startsWith('/') ? requestedPath : `/${requestedPath}`}`;
    const targetUrl = `${targetPath}${hash ? `#${hash}` : ''}`;

    window.history.pushState(null, '', targetUrl);
    setCurrentPath(targetUrl);
    window.requestAnimationFrame(() => {
      if (hash) document.getElementById(hash)?.scrollIntoView();
      else window.scrollTo({ top: 0, behavior: 'instant' });
    });
  };

  const hash = (currentPath.split('#')[1] || '').toLowerCase();
  let logicalRoute = normalizeDocsPath(currentPath);

  // Redirect legacy anchor URLs on monolithic sections to dedicated child pages
  if (logicalRoute === '/foundations' && hash && foundationHashRedirects[hash]) {
    logicalRoute = foundationHashRedirects[hash];
  } else if (logicalRoute === '/architecture' && hash && architectureHashRedirects[hash]) {
    logicalRoute = architectureHashRedirects[hash];
  } else if (logicalRoute === '/docs' || logicalRoute === '/getting-started') {
    logicalRoute = '/docs/introduction';
  }

  const renderContent = () => {
    if (logicalRoute === '/') {
      return <HomePage onNavigate={navigate} />;
    }

    const documentationPage = documentationPages.find(page => page.path === logicalRoute);
    if (documentationPage) {
      return <DocumentationPage page={documentationPage} onNavigate={navigate} />;
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

    if (logicalRoute === '/dev/markdown') {
      return <MarkdownGalleryPage />;
    }

    return <NotFoundPage onNavigate={navigate} />;
  };

  return (
    <Shell currentPath={logicalRoute} onNavigate={navigate}>
      {renderContent()}
    </Shell>
  );
};
