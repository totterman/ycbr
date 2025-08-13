import { useQuery } from '@tanstack/react-query';
import axios from 'axios';

interface UserinfoDto {
  username: string;
  email: string;
  firstname: string;
  lastname: string;
  roles: string[];
  exp: number;
}

export class User {
  static readonly ANONYMOUS = new User("", "", "", "", []);

  constructor(
    readonly name: string,
    readonly email: string,
    readonly firstname: string,
    readonly lastname: string,
    readonly roles: string[]
  ) {}

  get isAuthenticated(): boolean {
    return !!this.name;
  }

  hasAnyRole(...roles: string[]): boolean {
    return roles.some(r => this.roles.includes(r));
  }
}

export function useUser() {
  const query = useQuery({
    queryKey: ['user'],
    queryFn: async () => {
      const { data } = await axios.get<UserinfoDto>(process.env.EXPO_PUBLIC_REVERSE_PROXY_URI + '/bff/api/me');
      return data;
    },
    select: (data) =>
      data.username
        ? new User(
            data.username || "",
            data.email || "",
            data.firstname || "",
            data.lastname || "",
            data.roles || []
          )
        : User.ANONYMOUS,
    refetchInterval: (query) => {
      const data = query.state.data;
      if (!data?.exp) return false;
      const now = Date.now();
      const delay = (1000 * data.exp - now) * 0.8;
      return delay > 2000 ? delay : false;
    },
    staleTime: 0,
  });

  if (query.isError) {
      console.error("Failed to fetch user data: ", query.error);
  }

  return {
    user: query.data || User.ANONYMOUS,
    isLoading: query.isLoading,
    isFetching: query.isFetching,
    isError: query.isError,
    error: query.error,
  };
}