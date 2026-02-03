import { createFileRoute, Link } from "@tanstack/react-router";
import { useIntlayer } from "react-intlayer";


export const Route = createFileRoute("/loginerror")({
  component: LoginErrorComponent,
  notFoundComponent: LoginErrorComponent,
  errorComponent: LoginErrorComponent,
});

function LoginErrorComponent() {
  const content = useIntlayer("routes");

  return (
    <>
      <div>
        <p>{content.loginerror_title}</p>
        <Link to="/">{content.loginerror_text}</Link>
      </div>
    </>
  );
}
