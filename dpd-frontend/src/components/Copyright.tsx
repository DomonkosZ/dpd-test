import Link from '@mui/material/Link';
import Typography from '@mui/material/Typography';

function Copyright(props: any) {
    return (
      <Typography variant="body2" color="#ccc" align="center" {...props}>
        {'Copyright © '}
        <Link color="inherit" href="#">
          Zoltán Domonkos
        </Link>{' '}
        {new Date().getFullYear()}
        {'.'}
      </Typography>
    );
  }

  export default Copyright;