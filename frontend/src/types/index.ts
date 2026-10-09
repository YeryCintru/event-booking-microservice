export interface User {
  email: string;
  token: string;
}

export interface Event {
  id: number;
  title: string;
  totalCapacity: number;
  availableCapacity: number;
}

export interface Booking {
  id: number;    
  userId: number;
  eventId: number;
  ticketsCount: number;
  status: string; // Ej: "CONFIRMED", "CANCELLED", "PENDING"
  createdAt: Date;
}

export interface BookingRequest {
  userId: number;
  eventId: number;
  ticketsCount: number;
}