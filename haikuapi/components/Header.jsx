import React from "react";
import { Link } from "react-router-dom";
import styled from "styled-components";

const Navbar = styled.nav`
  background-color: #333;
  padding: 10px;
  display: flex;
  justify-content: center;

  a {
    color: white;
    margin: 0 10px;
    text-decoration: none;

    &:hover {
      text-decoration: underline;
    }
  }
`;

function Header() {
  return (
    <Navbar>
      <Link to="/home">Home</Link>
      <Link to="/vision">Vision</Link>
      <Link to="/endpoints">Endpoints</Link>
    </Navbar>
  );
}
export default Header;
