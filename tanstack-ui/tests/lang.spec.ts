import { test, expect } from '@playwright/test';

test('select English language', async ({ page }) => {
  // Navigate to the Playwright homepage
  await page.goto('http://king:7080/tanstack-ui');
  
  // Click the lang button
//  await page.getByRole('button', { name: /language-selector/i }).click();
  
await page.getByRole('button', { name: 'language-selector' }).click();
await page.getByRole('menuitem', { name: 'English' }).click();
await expect(page.getByRole('button', { name: 'Login' })).toBeVisible();
expect(page.getByRole('link', { name: 'Home' })).toBeVisible();
});

test('select Finnish language', async ({ page }) => {
  // Navigate to the Playwright homepage
  await page.goto('http://king:7080/tanstack-ui');
  
  // Click the lang button
//  await page.getByRole('button', { name: /language-selector/i }).click();
  
await page.getByRole('button', { name: 'language-selector' }).click();
await page.getByRole('menuitem', { name: 'suomi (Suomi)' }).click();
await expect(page.getByRole('button', { name: 'Kirjaudu' })).toBeVisible();
expect(page.getByRole('link', { name: 'Aloitussivu' })).toBeVisible();
});

test('select Swedish language', async ({ page }) => {
  // Navigate to the Playwright homepage
  await page.goto('http://king:7080/tanstack-ui');
  
  // Click the lang button
//  await page.getByRole('button', { name: /language-selector/i }).click();
  
await page.getByRole('button', { name: 'language-selector' }).click();
await page.getByRole('menuitem', { name: 'svenska (Finland)' }).click();
await expect(page.getByRole('button', { name: 'Logga in' })).toBeVisible();
expect(page.getByRole('link', { name: 'Startsida' })).toBeVisible();
});
