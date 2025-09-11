import { jsx } from 'react/jsx-runtime';
import { T as Typography } from './ssr.mjs';
import '@tanstack/react-router';
import 'react';
import 'stylis';
import 'react-is';
import 'prop-types';
import 'clsx';
import '@babel/runtime/helpers/esm/extends';
import 'zod';
import '@tanstack/history';
import '@tanstack/router-core/ssr/client';
import '@tanstack/router-core';
import '@tanstack/router-core/ssr/server';
import 'node:async_hooks';
import 'tiny-invariant';
import '@tanstack/react-router/ssr/server';

function RouteComponent() {
  return /* @__PURE__ */ jsx(Typography, { variant: "h2", children: "About" });
}

export { RouteComponent as component };
//# sourceMappingURL=about-CEY-teDM.mjs.map
