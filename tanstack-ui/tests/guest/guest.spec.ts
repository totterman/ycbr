import { test, expect } from "@playwright/test";

test.describe("Log in with role GUEST", () => {
  test("Login etc", async ({ page, context }) => {
    await page.goto("http://king:7080/tanstack-ui");

    await page.getByRole("button", { name: "language-selector" }).click();
    await page.getByRole("menuitem", { name: "English" }).click();
    await page.getByRole("button", { name: "Login" }).click();

    await page.getByRole('textbox', { name: 'Username or email' }).fill('jenny');
    await page.getByRole('textbox', { name: 'Password' }).fill('jenny');
    await page.getByRole('button', { name: 'Sign In' }).click();
    await page.waitForURL("http://king:7080/tanstack-ui");
    
    await expect(page.getByRole('link', { name: 'Startsida' })).toBeVisible();
    expect(page.getByRole('link', { name: 'Båtar' })).toBeVisible();

    await page.getByRole('link', { name: 'Båtar' }).click();
    await page.waitForURL("http://king:7080/tanstack-ui/boats");

    await expect(page.getByText('Båtens namn')).toBeVisible();
    await expect(page.getByText('-10 av 21')).toBeVisible();
    await expect(page.getByRole('button', { name: 'Visa/Göm sök' })).toBeVisible();
  });
});
