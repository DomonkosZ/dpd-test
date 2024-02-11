import React from "react";
import AppBar from "@mui/material/AppBar";
import Toolbar from "@mui/material/Toolbar";
import Button from "@mui/material/Button";
import Typography from "@mui/material/Typography";
import {Outlet} from "react-router-dom";

function Layout() {
  return (
      <div>
        <AppBar position="static">
          <Toolbar>
            <Typography variant="h6" style={{ flex: 1 }}>
              Customer admin
            </Typography>
            <Button color="inherit" href="/">
              Customer table
            </Button>
            <Button color="inherit" href="/userpage">
              New customer
            </Button>
          </Toolbar>
        </AppBar>
        <Outlet />
      </div>
  );
}

export default Layout;