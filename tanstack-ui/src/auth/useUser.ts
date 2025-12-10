import { BoatType } from '@/boats/boat';
import { InspectionDto } from '@/inspections/inspection';
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

  get isBoatowner(): boolean {
    return this.roles.includes('boatowner');
  }

  get isInspector(): boolean {
    return this.roles.includes('inspector');
  }

  get isStaff(): boolean {
    return this.roles.includes('staff');
  }

}

export function useUser() {
  const query = useQuery({
    queryKey: ['user'],
    queryFn: async () => {
      const { data } = await axios.get<UserinfoDto>('/bff/api/me');
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

  //
  //
  // boatowners only: fetch own boats
  //
  //
  const {
    status: boatStatus, fetchStatus: fetchBoatStatus, data: myBoats
  } = useQuery({
    queryKey: ['boats', query.data?.name],
    queryFn: async () => {
      const { data } = await axios.get<BoatType[]>('/bff/api/boats/owner', { params: {
        name: query.data?.name,
      } });
      return data;
    },
    enabled: !!query.data && query.data.isBoatowner,
  });

  //
  //
  // inspectors only: fetch own inspections
  //
  //
    const {
    status: inspectionStatus, fetchStatus: fetchinspectionStatus, data: myInspections
  } = useQuery({
    queryKey: ['inspections', query.data?.name],
    queryFn: async () => {
      const { data } = await axios.get<InspectionDto[]>('/bff/api/inspections/inspector', { params: {
        name: query.data?.name,
      } });
      return data;
    },
    enabled: !!query.data && query.data.isInspector,
  });


  return {
    user: query.data || User.ANONYMOUS,
    myBoats: myBoats,
    myInspections: myInspections,
    isLoading: query.isLoading,
    isFetching: query.isFetching,
    isError: query.isError,
    error: query.error,
  };
}