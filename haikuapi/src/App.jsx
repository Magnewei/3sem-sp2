import { Routes, Route } from "react-router-dom";
import Header from "../components/Header";
import Vision from "../components/Vision";
import Endpoints from "../components/endpoints";
import styled from "styled-components";
import GlobalLayout from "../components/GlobalLayout";

const AppContainer = styled.div`
  font-family: Arial, sans-serif;
  padding: 0;
  margin: 0;

  main {
    padding: 20px;
    display: flex;
    justify-content: center;
  }
`;

function App() {
  return (
    <div className="App">
      <Header />
      <GlobalLayout>
        <Routes>
          <Route path="home" element={<div>Welcome to the Home Page!</div>} />
          <Route path="vision" element={<Vision />} />
          <Route path="endpoints" element={<Endpoints />} />
        </Routes>
      </GlobalLayout>
    </div>
  );
}

export default App;
