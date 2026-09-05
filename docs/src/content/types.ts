import { CalloutType } from '../components/ui/Callout';

export interface DocumentationCode {
  language: string;
  title: string;
  value: string;
}

export interface DocumentationCallout {
  type: CalloutType;
  title: string;
  value: string;
}

export interface DocumentationSection {
  id: string;
  title: string;
  paragraphs: string[];
  bullets?: string[];
  visual?: string;
  code?: DocumentationCode;
  callout?: DocumentationCallout;
}

export interface DocumentationPageDefinition {
  path: string;
  eyebrow: string;
  title: string;
  description: string;
  intro: string;
  sections: DocumentationSection[];
}
