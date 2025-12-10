// File: `tanstack-ui/src/components/CustomButtonLink.spec.tsx`
import React from "react";
import { describe, it, expect, beforeEach, vi } from "vitest";
import { render, screen } from "@testing-library/react";

// Mock @tanstack/react-router.createLink to produce a component that forwards props and ref
vi.mock("@tanstack/react-router", () => {
  const React = require("react");
  return {
    createLink: (Comp: any) =>
      React.forwardRef((props: any, ref: any) => {
        // keep preload visible on the DOM as data-preload for assertions
        const { preload, ...rest } = props;
        return React.createElement(Comp, { ref, ...rest, "data-preload": preload });
      }),
  };
});

// Mock MUI Button to render the requested element (component prop) and forward ref
vi.mock("@mui/material", () => {
  const React = require("react");
  const Button = React.forwardRef(({ component, children, ...props }: any, ref: any) => {
    const Tag = component || "button";
    return React.createElement(Tag, { ref, ...props }, children);
  });
  return { Button };
});

import { CustomButtonLink } from "./CustomButtonLink";

describe("CustomButtonLink", () => {
  beforeEach(() => {
    vi.clearAllMocks();
  });

  it("renders an anchor and includes the injected preload prop as data-preload", () => {
    render(React.createElement(CustomButtonLink, { href: "/path", children: "Click me" }));

    const anchor = screen.getByText("Click me").closest("a");
    expect(anchor).toBeTruthy();
    expect(anchor?.getAttribute("href")?.endsWith("/path")).toBeTruthy();
    expect(anchor?.getAttribute("data-preload")).toBe("intent");
  });

  it("forwards ref to the underlying anchor element", () => {
    const ref = React.createRef<HTMLAnchorElement>();
    render(React.createElement(CustomButtonLink, { href: "/ref-test", ref, children: "Ref" }));

    // after render the ref should point to the anchor element
    expect(ref.current).toBeInstanceOf(HTMLAnchorElement);
    expect(ref.current?.getAttribute("href")?.endsWith("/ref-test")).toBeTruthy();
  });

  it("passes through arbitrary props (e.g., className)", () => {
    render(
      React.createElement(CustomButtonLink, {
        href: "/klass",
        className: "my-class",
        children: "WithClass",
      }),
    );

    const anchor = screen.getByText("WithClass").closest("a");
    expect(anchor).toBeTruthy();
    expect(anchor?.classList.contains("my-class")).toBeTruthy();
  });
});
