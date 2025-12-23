import { expect, test } from "@playwright/test";

test("Book boat", async ({ page }) => {

await page.goto('http://king:7080/tanstack-ui');
await page.getByRole('button', { name: 'Logga in' }).click();
await page.getByRole('textbox', { name: 'Username or email' }).click();
await page.getByRole('textbox', { name: 'Username or email' }).fill('ronja');
await page.getByRole('textbox', { name: 'Password' }).click();
await page.getByRole('textbox', { name: 'Password' }).fill('ronja');
await page.getByRole('button', { name: 'Sign In' }).click();

await expect(page.getByRole('link', { name: 'Båtar' })).toBeVisible();
await expect(page.getByRole('link', { name: 'Besiktningstillfällen' })).toBeVisible();

await page.getByRole('link', { name: 'Besiktningstillfällen' }).click();
await expect(page.getByText('Åtgärder')).toBeVisible();

await page.getByRole('row', { name: 'Boka besiktning 7.5.2026 Bjö' }).getByLabel('Boka besiktning').click();
await expect(page.locator('#booking-dialog-title')).toContainText('Boka båtbesiktning');

await page.getByRole('combobox', { name: 'Båtens namn' }).click();
await expect(page.getByRole('listbox')).toContainText('Tidal Traveler');

await page.getByRole('option', { name: 'Tidal Traveler' }).click();
await expect(page.getByRole('button', { name: 'Avbryt' })).toBeVisible();
await expect(page.getByRole('button', { name: 'Boka' })).toBeVisible();

await page.getByRole('button', { name: 'Boka' }).click();
await expect(page.locator('tbody')).toContainText('1');
});