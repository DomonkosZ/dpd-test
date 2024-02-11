import React, {useState, useEffect} from 'react';
import {DataGrid, GridColDef, GridRenderCellParams} from '@mui/x-data-grid';
import Typography from "@mui/material/Typography";
import Button from "@mui/material/Button";
import {IconButton, Snackbar} from "@mui/material";
import CloseIcon from "@mui/icons-material/Close";

const API_BASE_URL = 'http://localhost:8080/api/customers';
const JSON_HEADER = {
  'Content-Type': 'application/json'
};

export default function Home() {
  const [customers, setCustomers] = useState([]);
  const [openSnackbar, setOpenSnackbar] = useState<boolean>(false);
  const [snackbarData, setSnackbarData] = useState<{
    message: string;
    color: string;
  }>({message: '', color: 'blue'});

  const fetchCustomers = async () => {
    try {
      const response = await fetch(API_BASE_URL);
      const data = await response.json();
      setCustomers(data.responseObject);
    } catch (error) {
      console.error('Error:', error);
    }
  };

  useEffect(() => {
    fetchCustomers();
  }, []);


  const updateCustomer = async (newVal: any) => {
    try {
      const response = await fetch(`${API_BASE_URL}/false`, {
        method: 'PUT',
        headers: JSON_HEADER,
        body: JSON.stringify(newVal)
      });
      if (response.ok) {
        setSnackbarData({message: "Successful save!", color: "green"});  // Usage of setSnackbarData
        setOpenSnackbar(true);  // Usage of setOpenSnackbar

      } else {
        setSnackbarData({message: "Save failed!", color: "red"});
        setOpenSnackbar(true);
      }
    } catch (error) {
      console.error(error);
    }
  };

  const gdprToCustomer = async (newVal: any) => {
    try {
      const response = await fetch(`${API_BASE_URL}/true`, {
        method: 'PUT',
        headers: JSON_HEADER,
        body: JSON.stringify(newVal)
      });
      if (response.ok) {
        setSnackbarData({message: "User GDPR changed!", color: "green"});
        setOpenSnackbar(true);
        fetchCustomers();
      } else {
        console.error("error");
      }
    } catch (error) {
      console.error(error);
    }
  };

  const renderList = (list: any, callback: Function) => list?.length ? list.map(callback) :
      <Typography>Not available</Typography>;

  if (customers.length === 0) {
    return <div>Loading...</div>;
  }

  const columns: GridColDef[] = [
    {field: 'id', headerName: 'ID'},
    {field: 'name', headerName: 'Name', editable: true},
    {field: 'dateOfBirth', headerName: 'Date Of Birth', editable: true},
    {field: 'placeOfBirth', headerName: 'Place Of Birth', editable: true},
    {field: 'motherName', headerName: 'Mother name', editable: true},
    {field: 'tajNumber', headerName: 'TAJ Number', editable: true},
    {field: 'taxIdentifier', headerName: 'Tax Identifier', editable: true},
    {field: 'email', headerName: 'Email', editable: true},
    {
      width: 300,
      field: 'addressList',
      headerName: 'Addresses',
      editable: false,
      renderCell: (params: GridRenderCellParams) => (
          <ul>
            {renderList(params.row.addressList, (address: {
              street: string;
              city: string;
              zipcode: string;
              houseNumber: string
            }, index: React.Key | null | undefined) => (
                <li key={index}>
                  <Typography>
                    {`${address.zipcode} - ${address.city}, ${address.street} ${address.houseNumber} `}
                  </Typography>
                </li>
            ))}
          </ul>
      ),
    },
    {
      width: 300,
      field: 'phoneNumbersList',
      headerName: 'Phone Numbers',
      renderCell: (params: GridRenderCellParams) => (
          <ul>
            {renderList(params.row.phoneNumbersList, (phone: {
              phoneNumber: any;
              phoneType: any
            }, index: React.Key | null | undefined) => (
                <li key={index}>
                  <Typography>{`${phone.phoneType}: ${phone.phoneNumber}`}</Typography>
                </li>
            ))}
          </ul>
      ),
    },
    {
      headerName: 'Go GDPR',
      field: 'gpdr',
      renderCell: (params: GridRenderCellParams) => (
          <>
            <Button onClick={() => gdprToCustomer(params.row)}>
              Go GDPR
            </Button>
          </>
    )
    }
  ];

  return (
      <div style={{height: 500, width: "100%"}}>
        <DataGrid rows={customers} columns={columns} processRowUpdate={updateCustomer}/>
        <Snackbar
            open={openSnackbar}
            autoHideDuration={6000}
            onClose={() => setOpenSnackbar(false)}
            message={snackbarData.message}
            style={{backgroundColor: snackbarData.color}}
            action={
              <React.Fragment>
                <IconButton size="small" aria-label="close" color="inherit"
                            onClick={() => setOpenSnackbar(false)}>
                  <CloseIcon fontSize="small"/>
                </IconButton>
              </React.Fragment>
            }
        />
      </div>
  );
}