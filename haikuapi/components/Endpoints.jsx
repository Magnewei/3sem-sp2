import React from "react";
import styled from "styled-components";

const Container = styled.div`
  max-width: 1000px;
  margin: 20px auto;
`;

const Table = styled.table`
  width: 100%;
  border-collapse: collapse;
  margin-bottom: 30px;

  th,
  td {
    border: 1px solid #ddd;
    padding: 10px;
    text-align: left;
  }

  th {
    background-color: #333;
    color: white;
  }

  @media (max-width: 768px) {
    th,
    td {
      font-size: 12px;
    }
  }
`;

const Title = styled.h3`
  margin-top: 20px;
`;

function Endpoints() {
  const haikuEndpoints = [
    {
      method: "GET",
      url: "/api/haikus",
      requestBody: "",
      responseBody: "[haiku1, haiku2]",
      error: "",
    },
    {
      method: "GET",
      url: "/api/haikus/{id}",
      requestBody: "",
      responseBody: "haiku",
      error: "(e1)",
    },
    {
      method: "POST",
      url: "/api/haikus",
      requestBody: "haiku without id",
      responseBody: "",
      error: "(e2)",
    },
    {
      method: "PUT",
      url: "/api/haikus/{id}",
      requestBody: "haiku without id",
      responseBody: "haiku",
      error: "",
    },
    {
      method: "DELETE",
      url: "/api/haikus/{id}",
      requestBody: "",
      responseBody: "",
      error: "",
    },
  ];

  const haikuPartEndpoints = [
    {
      method: "GET",
      url: "/api/haikuparts",
      requestBody: "",
      responseBody: "[haikuPart1, haikuPart2]",
      error: "",
    },
    {
      method: "GET",
      url: "/api/haikuparts/{id}",
      requestBody: "",
      responseBody: "haikuPart",
      error: "",
    },
    {
      method: "POST",
      url: "/api/haikuparts",
      requestBody: "haikuPart without id",
      responseBody: "",
      error: "",
    },
    {
      method: "PUT",
      url: "/api/haikuparts/{id}",
      requestBody: "haikuPart without id",
      responseBody: "haikuPart",
      error: "",
    },
    {
      method: "DELETE",
      url: "/api/haikuparts/{id}",
      requestBody: "",
      responseBody: "",
      error: "",
    },
  ];

  return (
    <Container>
      <Title>Haiku Endpoints</Title>
      <Table>
        <thead>
          <tr>
            <th>Method</th>
            <th>URL</th>
            <th>Request Body</th>
            <th>Response</th>
            <th>Error</th>
          </tr>
        </thead>
        <tbody>
          {haikuEndpoints.map((endpoint, idx) => (
            <tr key={idx}>
              <td>{endpoint.method}</td>
              <td>{endpoint.url}</td>
              <td>{endpoint.requestBody}</td>
              <td>{endpoint.responseBody}</td>
              <td>{endpoint.error}</td>
            </tr>
          ))}
        </tbody>
      </Table>

      <Title>Haiku Part Endpoints</Title>
      <Table>
        <thead>
          <tr>
            <th>Method</th>
            <th>URL</th>
            <th>Request Body</th>
            <th>Response</th>
            <th>Error</th>
          </tr>
        </thead>
        <tbody>
          {haikuPartEndpoints.map((endpoint, idx) => (
            <tr key={idx}>
              <td>{endpoint.method}</td>
              <td>{endpoint.url}</td>
              <td>{endpoint.requestBody}</td>
              <td>{endpoint.responseBody}</td>
              <td>{endpoint.error}</td>
            </tr>
          ))}
        </tbody>
      </Table>
    </Container>
  );
}

export default Endpoints;
