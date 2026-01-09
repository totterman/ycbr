import { test, expect } from "@playwright/test";
const APP_URI = process.env.VITE_REVERSE_PROXY + '' + process.env.VITE_BASE_PATH;

test.describe("Log in with role BOATOWNER", () => {
  test("Login etc", async ({ page, context }) => {
    await page.goto('');

    await page.getByRole("button", { name: "language-selector" }).click();
    await page.getByRole("menuitem", { name: "English" }).click();
    await page.getByRole("button", { name: "Login" }).click();

    await page.getByRole('textbox', { name: 'Username or email' }).fill('stina');
    await page.getByRole('textbox', { name: 'Password' }).fill('stina');
    await page.getByRole('button', { name: 'Sign In' }).click();
    await page.waitForURL(APP_URI);
    
    await expect(page.getByRole('link', { name: 'Startsida' })).toBeVisible();
    expect(page.getByRole('link', { name: 'Båtar' })).toBeVisible();
    expect(page.getByRole('link', { name: 'Besiktningstillfällen' })).toBeVisible();

    await page.getByRole('link', { name: 'Båtar' }).click();
    await page.waitForURL(APP_URI + "/boats");

    await expect(page.getByText('Båtens namn')).toBeVisible();
    await expect(page.getByText('-10 av 21')).toBeVisible();
    await expect(page.getByRole('button', { name: 'Visa/Göm sök' })).toBeVisible();
  });
});
