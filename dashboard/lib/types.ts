export type Form = {
  href: string;
  contentType: string;
  op: string;
  htv: {
    methodName: string;
  };
};

export type ComponentProperties = {
  id: string;
  title: string;
  description: string;
  type: string;
  properties: Record<string, string>;
  readOnly: boolean;
  forms: Form[];
};

export type Input = {
  type: string;
  properties: Record<string, string>;
};

export type ComponentActions = {
  id: string;
  title: string;
  description: string;
  input: Input;
  forms: Form[];
};

export type ThingDescription = {
  title: string;
  description: string;
  properties: Record<string, ComponentProperties>;
  actions: Record<string, ComponentActions>;
};

export type ParsedThingDescription = {
  title: string;
  description: string;
  properties: ComponentProperties[];
  actions: ComponentActions[];
};
