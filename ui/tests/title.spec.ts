import { test, expect } from '@playwright/test';

test('homepage has Yacht Club Boat Register in title', async ({ page }) => {
  // Navigate to the Playwright homepage
  await page.goto('');
  
  // Fetch the title of the page
  const title = await page.title();
  
  // Assert that the title contains 'Playwright'
  expect(title).toContain('Yacht Club Boat Register');
});
