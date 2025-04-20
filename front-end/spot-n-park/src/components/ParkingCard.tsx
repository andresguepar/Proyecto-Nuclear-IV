import React from 'react';
import { Card, CardContent, Typography, Rating, Box } from '@mui/material';
import LocationOnIcon from '@mui/icons-material/LocationOn';
import AttachMoneyIcon from '@mui/icons-material/AttachMoney';

interface ParkingCardProps {
  parking: {
    id: string;
    name: string;
    address: string;
    rating: number;
    price: number;
    imageUrl: string;
  };
  onClick: (parkingId: string) => void;
}

const ParkingCard: React.FC<ParkingCardProps> = ({ parking, onClick }) => {
  return (
    <Card 
      sx={{ 
        maxWidth: 345, 
        cursor: 'pointer',
        transition: 'transform 0.2s',
        '&:hover': {
          transform: 'scale(1.02)',
        }
      }}
      onClick={() => onClick(parking.id)}
    >
      <Box
        component="img"
        sx={{
          height: 200,
          width: '100%',
          objectFit: 'cover',
        }}
        src={parking.imageUrl}
        alt={parking.name}
      />
      <CardContent>
        <Typography gutterBottom variant="h5" component="div">
          {parking.name}
        </Typography>
        <Box sx={{ display: 'flex', alignItems: 'center', mb: 1 }}>
          <LocationOnIcon color="primary" sx={{ mr: 1 }} />
          <Typography variant="body2" color="text.secondary">
            {parking.address}
          </Typography>
        </Box>
        <Box sx={{ display: 'flex', alignItems: 'center', mb: 1 }}>
          <Rating value={parking.rating} readOnly precision={0.5} />
          <Typography variant="body2" color="text.secondary" sx={{ ml: 1 }}>
            ({parking.rating})
          </Typography>
        </Box>
        <Box sx={{ display: 'flex', alignItems: 'center' }}>
          <AttachMoneyIcon color="success" />
          <Typography variant="body1" color="text.primary">
            {parking.price}/hora
          </Typography>
        </Box>
      </CardContent>
    </Card>
  );
};

export default ParkingCard; 