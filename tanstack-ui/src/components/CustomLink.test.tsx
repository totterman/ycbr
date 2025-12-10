// File: `tanstack-ui/src/components/CustomLink.spec.tsx`
import React from "react";
import { render, screen } from "@testing-library/react";
import { describe, it, expect, beforeEach, vi } from "vitest";

vi.mock("@tanstack/react-router", () => {
  const React = require("react");
  return {
    createLink: (Comp: any) =>
      React.forwardRef((props: any, ref: any) => {
        const { preload, ...rest } = props;
        // expose preload as data-preload for assertions
        return React.createElement(Comp, { ref, ...rest, "data-preload": preload });
      }),
  };
});

vi.mock("@mui/material", () => {
  const React = require("react");
  // Render an anchor and forward ref so tests can assert on the DOM element
  const Link = React.forwardRef(({ children, ...props }: any, ref: any) =>
    React.createElement("a", { ref, ...props }, children),
  );
  return { Link };
});

import { CustomLink } from "./CustomLink";

describe("CustomLink", () => {
  beforeEach(() => {
    vi.clearAllMocks();
  });

  it("renders an anchor and includes injected preload as data-preload", () => {
    render(React.createElement(CustomLink, { href: "/path", children: "Click me" }));

    const anchor = screen.getByText("Click me").closest("a");
    expect(anchor).toBeTruthy();
    expect(anchor?.getAttribute("href")?.endsWith("/path")).toBeTruthy();
    expect(anchor?.getAttribute("data-preload")).toBe("intent");
  });

  it("forwards ref to the underlying anchor element", () => {
    const ref = React.createRef<HTMLAnchorElement>();
    render(React.createElement(CustomLink, { href: "/ref-test", ref, children: "Ref" }));

    expect(ref.current).toBeInstanceOf(HTMLAnchorElement);
    expect(ref.current?.getAttribute("href")?.endsWith("/ref-test")).toBeTruthy();
  });

  it("passes through arbitrary props (e.g., className)", () => {
    render(
      React.createElement(CustomLink, {
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
