import { useUser } from '@/hooks/useUser';
import { SplashScreen } from 'expo-router';

export function SplashScreenController() {
  const { isLoading } = useUser();

  if (!isLoading) {
    SplashScreen.hideAsync();
  }

  return null;
}
