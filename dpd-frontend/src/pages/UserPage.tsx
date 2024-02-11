import {AccountCircle} from '@mui/icons-material';
import Avatar from '@mui/material/Avatar';
import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import Container from '@mui/material/Container';
import CssBaseline from '@mui/material/CssBaseline';
import Grid from '@mui/material/Grid';
import {createTheme, ThemeProvider} from '@mui/material/styles';
import TextField from '@mui/material/TextField';
import Typography from '@mui/material/Typography';
import React, {useCallback, useEffect, useState} from "react";
import Copyright from "../components/Copyright";
import {IconButton, MenuItem, Select, Snackbar} from "@mui/material";
import CloseIcon from '@mui/icons-material/Close';

interface PersonData {
  id: string;
  name: string;
  dateOfBirth: string;
  placeOfBirth: string;
  motherName: string;
  tajNumber: string;
  taxIdentifier: string;
  email: string;
  addressList: Address[];
  phoneNumbersList: Phones[];
}

interface Address {
  zipcode: string;
  city: string;
  street: string;
  houseNumber: string;
}

interface Phones {
  phoneNumber: string;
  phoneType: string;
}

function UserPage() {
  const [openSnackbar, setOpenSnackbar] = useState<boolean>(false);
  const [snackbarData, setSnackbarData] = useState<{
    message: string;
    color: string;
  }>({message: '', color: 'blue'});
  const [submitActive, setSubmitActive] = useState<boolean>(true);
  const [submitting, setSubmitting] = useState(false);
  const [personData, setPersonData] = useState<PersonData>({
    id: "",
    name: "",
    dateOfBirth: "",
    placeOfBirth: "",
    motherName: "",
    tajNumber: "",
    taxIdentifier: "",
    email: "",
    addressList: [],
    phoneNumbersList: []
  });
  const [errors, setErrors] = useState({
    name: "",
    dateOfBirth: "",
    placeOfBirth: "",
    motherName: "",
    tajNumber: "",
    taxIdentifier: "",
    email: ""
  });

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const {name, value} = e.target;
    setPersonData({...personData, [name]: value});
  };

  const handleAddressChange = (
      index: number,
      field: keyof Address,
      value: string
  ) => {
    const updatedAddresses = [...personData.addressList];
    updatedAddresses[index][field] = value;
    setPersonData({...personData, addressList: updatedAddresses});
  };

  const addAddress = () => {
    setPersonData({
      ...personData,
      addressList: [
        ...personData.addressList,
        {zipcode: "", city: "", street: "", houseNumber: ""},
      ],
    });
  };

  // Delete address
  const deleteAddress = (index: number) => {
    const updatedAddresses = [...personData.addressList];
    updatedAddresses.splice(index, 1);
    setPersonData({...personData, addressList: updatedAddresses});
  };


  const handlePhoneNumberChange = (
      index: number,
      field: keyof Phones,
      value: string
  ) => {
    const updatedPhoneNumbers = [...personData.phoneNumbersList];
    updatedPhoneNumbers[index][field] = value;
    setPersonData({...personData, phoneNumbersList: updatedPhoneNumbers});
  };

  const addPhoneNumber = () => {
    setPersonData({
      ...personData,
      phoneNumbersList: [...personData.phoneNumbersList,
        {
          phoneNumber: "", phoneType: "mobile"
        }],
    });
  };

  const deletePhoneNumber = (index: number) => {
    const updatedPhoneNumbers = [...personData.phoneNumbersList];
    updatedPhoneNumbers.splice(index, 1);
    setPersonData({...personData, phoneNumbersList: updatedPhoneNumbers});
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setErrors(validateValues(personData));
    setSubmitting(true);
  };

  const validateValues = (personData: PersonData) => {
    // Errors object is reset at the start of every validation
    let errors = {
      name: "",
      dateOfBirth: "",
      placeOfBirth: "",
      motherName: "",
      tajNumber: "",
      taxIdentifier: "",
      email: ""
    };

    // Regex for validating email format.
    let emailPattern = /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$/i;
    if (personData.email.length < 15 || personData.email === "") {
      errors.email = "Email is too short";
    }
    if (!emailPattern.test(personData.email)) {
      errors.email = "Email format is not valid";
    }
    if (personData.name.length < 5 || personData.name === "") {
      errors.name = "The field cannot be empty and must be at least 5 characters long!";
    }
    if (personData.placeOfBirth === "") {
      errors.placeOfBirth = "The field cannot be empty";
    }
    if (personData.dateOfBirth === "") {
      errors.dateOfBirth = "The field cannot be empty";
    }
    if (personData.motherName.length < 5 || personData.motherName === "") {
      errors.motherName = "The field cannot be empty and must be at least 5 characters long!";
    }
    if (personData.tajNumber.length < 9 || personData.tajNumber === "") {
      errors.tajNumber = "The field cannot be empty and must be at least 9 characters long!";
    }
    if (personData.taxIdentifier.length < 10 || personData.taxIdentifier === "") {
      errors.taxIdentifier = "The field cannot be empty and must be at least 10 characters long!";
    }
    return errors;
  };


  const doSubmit = useCallback(async () => {
    const response = await fetch('http://localhost:8080/api/customers', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(personData)
    });

    if (response.ok) {
      const data = await response.json();
      console.log(data);
      setSnackbarData({message: "Successful save!", color: "green"});
      setOpenSnackbar(true);
      setSubmitActive(false);
    } else {
      const data = await response.json();
      setSnackbarData({message: "Save failed!", color: "red"});
      setOpenSnackbar(true);
      console.error(`HTTP error! status: ${response.status}`);
    }
  }, [personData, setSnackbarData, setOpenSnackbar, setSubmitActive]);

  useEffect(() => {
    const errorCount = Object.values(errors).filter(error => error !== "").length;
    if (errorCount === 0 && submitting) {
      doSubmit();
    }
  }, [errors, submitting, doSubmit]);

  const defaultTheme = createTheme();

  return (
      <>
        <ThemeProvider theme={defaultTheme}>
          <Container component="main" maxWidth="xs">
            <CssBaseline/>
            <Box
                sx={{
                  marginTop: 8,
                  display: 'flex',
                  flexDirection: 'column',
                  alignItems: 'center',
                }}
            >
              <Avatar sx={{m: 1, bgcolor: 'secondary.main'}}>
                <AccountCircle/>
              </Avatar>
              <Typography component="h1" variant="h5">
                Create new customer
              </Typography>
              <Box component="form" noValidate onSubmit={handleSubmit} sx={{mt: 3}}>
                <Grid container spacing={2}>
                  <Grid item xs={12}>
                    <TextField
                        required
                        fullWidth
                        id="name"
                        label="Full name"
                        name="name"
                        autoComplete="name"
                        onChange={handleInputChange}
                    />
                    {errors.name ? (
                        <p style={{color: 'red'}}>
                          {errors.name}
                        </p>
                    ) : null}
                  </Grid>
                  <Grid item xs={12}>
                    <TextField
                        required
                        fullWidth
                        id="dateOfBirth"
                        name="dateOfBirth"
                        label="Birthday"
                        type="date"
                        defaultValue={new Date()}
                        InputLabelProps={{
                          shrink: true,
                        }}
                        onChange={handleInputChange}
                    />
                    {errors.dateOfBirth ? (
                        <p style={{color: 'red'}}>
                          {errors.dateOfBirth}
                        </p>
                    ) : null}
                  </Grid>
                  <Grid item xs={12}>
                    <TextField
                        required
                        fullWidth
                        name="placeOfBirth"
                        label="Place of birth"
                        id="placeOfBirth"
                        autoComplete="placeOfBirth"
                        onChange={handleInputChange}
                    />
                    {errors.placeOfBirth ? (
                        <p style={{color: 'red'}}>
                          {errors.placeOfBirth}
                        </p>
                    ) : null}
                  </Grid>
                  <Grid item xs={12}>
                    <TextField
                        required
                        fullWidth
                        name="motherName"
                        label="Mother name"
                        id="motherName"
                        autoComplete="motherName"
                        onChange={handleInputChange}
                    />
                    {errors.motherName ? (
                        <p style={{color: 'red'}}>
                          {errors.motherName}
                        </p>
                    ) : null}
                  </Grid>
                  <Grid item xs={12}>
                    <TextField
                        required
                        fullWidth
                        name="tajNumber"
                        label="Taj number"
                        id="tajNumber"
                        autoComplete="tajNumber"
                        onChange={handleInputChange}
                    />
                    {errors.tajNumber ? (
                        <p style={{color: 'red'}}>
                          {errors.tajNumber}
                        </p>
                    ) : null}
                  </Grid>
                  <Grid item xs={12}>
                    <TextField
                        required
                        fullWidth
                        name="taxIdentifier"
                        label="Tax identifier"
                        id="taxIdentifier"
                        autoComplete="taxIdentifier"
                        onChange={handleInputChange}
                    />
                    {errors.taxIdentifier ? (
                        <p style={{color: 'red'}}>
                          {errors.taxIdentifier}
                        </p>
                    ) : null}
                  </Grid>
                  <Grid item xs={12}>
                    <TextField
                        required
                        fullWidth
                        name="email"
                        label="E-mail"
                        id="email"
                        type="email"
                        autoComplete="email"
                        onChange={handleInputChange}
                    />
                    {errors.email ? (
                        <p style={{color: 'red'}}>
                          {errors.email}
                        </p>
                    ) : null}
                  </Grid>
                </Grid>

                {personData.addressList.map((address, index) => (
                    <Grid container spacing={2} key={index} style={{marginTop: 10}}>
                      <Grid item xs={12}>
                        <TextField
                            fullWidth
                            name="city"
                            label="City"
                            id="city"
                            autoComplete="city"
                            value={address.city}
                            onChange={(e) => handleAddressChange(index, "city", e.target.value)}
                        />
                      </Grid>
                      <Grid item xs={12}>
                        <TextField
                            fullWidth
                            name="street"
                            label="Street"
                            id="street"
                            autoComplete="street"
                            value={address.street}
                            onChange={(e) => handleAddressChange(index, "street", e.target.value)}
                        />
                      </Grid>
                      <Grid item xs={12} sm={6}>
                        <TextField
                            fullWidth
                            name="zipCode"
                            label="Zip Code"
                            id="zipCode"
                            autoComplete="zipCode"
                            value={address.zipcode}
                            onChange={(e) => handleAddressChange(index, "zipcode", e.target.value)}
                        />
                      </Grid>
                      <Grid item xs={12} sm={6}>
                        <TextField
                            fullWidth
                            name="houseNumber"
                            label="House Number"
                            id="houseNumber"
                            autoComplete="houseNumber"
                            value={address.houseNumber}
                            onChange={(e) =>
                                handleAddressChange(index, "houseNumber", e.target.value)
                            }
                        />
                      </Grid>
                      <Button color="error" onClick={() => deleteAddress(index)}>Delete</Button>
                    </Grid>
                ))}

                <Button onClick={addAddress} disabled={!submitActive}>Add New Address</Button>


                {personData.phoneNumbersList.map((phoneNumber, index) => (
                    <Grid container spacing={2} key={index} style={{marginTop: 10}}>
                      <Grid item xs={12} sm={6}>
                        <TextField
                            fullWidth
                            name="phoneNumber"
                            label="Phone number"
                            id="phoneNumber"
                            autoComplete="phoneNumber"
                            value={phoneNumber.phoneNumber}
                            onChange={(e) =>
                                handlePhoneNumberChange(index, "phoneNumber", e.target.value)
                            }
                        />
                      </Grid>
                      <Grid item xs={12} sm={6}>
                        <Select
                            required
                            fullWidth
                            labelId="demo-simple-select-label"
                            id="demo-simple-select"
                            value={phoneNumber.phoneType}
                            label="Phone type"
                            onChange={(e) =>
                                handlePhoneNumberChange(index, "phoneType", e.target.value)
                            }
                        >
                          <MenuItem value="mobile">Mobile</MenuItem>
                          <MenuItem value="home">Home</MenuItem>
                        </Select>
                      </Grid>
                      <Button color="error" onClick={() => deletePhoneNumber(index)}>Delete</Button>
                    </Grid>
                ))}
                <Button onClick={addPhoneNumber} disabled={!submitActive}>Add New Phone
                  Number</Button>


                <Button
                    disabled={!submitActive}
                    type="submit"
                    fullWidth
                    variant="contained"
                    sx={{mt: 3, mb: 2}}
                >
                  Save
                </Button>
              </Box>
            </Box>
            <Copyright sx={{mt: 5}}/>
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
          </Container>
        </ThemeProvider>
      </>
  );
}

export default UserPage;
