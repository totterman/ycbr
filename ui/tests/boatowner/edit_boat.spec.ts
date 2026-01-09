import { expect, test } from "@playwright/test";
const APP_URI = process.env.VITE_REVERSE_PROXY + '' + process.env.VITE_BASE_PATH;

test("Edit boat", async ({ page }) => {
  await page.goto('');
  await page.getByRole("button", { name: "Logga in" }).click();
  await page.getByRole("textbox", { name: "Username or email" }).click();
  await page.getByRole("textbox", { name: "Username or email" }).fill("ronja");
  await page.getByRole("textbox", { name: "Password" }).click();
  await page.getByRole("textbox", { name: "Password" }).fill("ronja");
  await page.getByRole("button", { name: "Sign In" }).click();
  await page.getByRole("link", { name: "Båtar" }).click();
  await page.getByRole("button", { name: "Gå till nästa sida" }).click();
  expect(page.getByText('Ägare')).toBeVisible();

  await page
    .locator(
      "tr:nth-child(19) > .MuiTableCell-root.MuiTableCell-body.MuiTableCell-alignLeft.MuiTableCell-sizeMedium.css-2oy1ci > span > .MuiButtonBase-root"
    )
    .click();
  await page
    .locator(
      "tr:nth-child(19) > .MuiTableCell-root.MuiTableCell-body.MuiTableCell-alignLeft.MuiTableCell-sizeMedium.css-2oy1ci > span > .MuiButtonBase-root"
    )
    .click();
  expect(page.getByText('ronja')).toBeVisible();
  await page.getByRole("button", { name: "Ändra" }).click();
  await page.getByRole("textbox", { name: "Motor" }).click();
  await page.getByRole("textbox", { name: "Motor" }).press("ArrowLeft");
  await page.getByRole("textbox", { name: "Motor" }).press("ArrowRight");
  await page.getByRole("textbox", { name: "Motor" }).fill("Kawasaki 1.5LT");
  await page.getByRole("button", { name: "Spara" }).click();
});
