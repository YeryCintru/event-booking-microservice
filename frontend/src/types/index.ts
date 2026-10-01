export interface User {
  username: string;
  token: string;
}

export interface Event {
  id: number;
  title: string;
  totalCapacity: number;
  availableCapacity: number;
}

export interface BookingRequest {
  userId: number;
  eventId: number;
  ticketsQuantity: number;
}