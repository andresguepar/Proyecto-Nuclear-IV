import React from 'react';
import { Grid, Container, Typography } from '@mui/material';
import ParkingCard from './ParkingCard';

interface Parking {
  id: string;
  name: string;
  address: string;
  rating: number;
  price: number;
  imageUrl: string;
}

interface ParkingListProps {
  parkings: Parking[];
  onParkingClick: (parkingId: string) => void;
}

const ParkingList: React.FC<ParkingListProps> = ({ parkings, onParkingClick }) => {
  if (parkings.length === 0) {
    return (
      <Container sx={{ py: 4 }}>
        <Typography variant="h6" align="center" color="text.secondary">
          No se encontraron estacionamientos
        </Typography>
      </Container>
    );
  }

  return (
    <Container sx={{ py: 4 }}>
      <Grid container spacing={3}>
        {parkings.map((parking) => (
          <Grid item key={parking.id} xs={12} sm={6} md={4}>
            <ParkingCard parking={parking} onClick={onParkingClick} />
          </Grid>
        ))}
      </Grid>
    </Container>
  );
};

export default ParkingList; 