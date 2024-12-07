import React from "react";
import styled from "styled-components";
import haiku1 from "../public/haiku1.jpg";
import haiku2 from "../public/haiku2.jpg";

const LayoutWrapper = styled.div`
  display: flex;
  justify-content: center;
  position: relative;
  width: 100%;
  min-height: 100vh;
`;

const ImageWrapper = styled.div`
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  img {
    width: 200px;
    height: 200px;
    object-fit: cover;
    border-radius: 50%;
  }

  @media (max-width: 768px) {
    img {
      width: 150px;
      height: 150px;
    }
  }
`;

const ContentWrapper = styled.div`
  width: 100%;
  max-width: 1200px;
  padding: 20px;
  z-index: 2;
  text-align: center;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  min-height: 100vh;
`;

function GlobalLayout({ children }) {
  return (
    <LayoutWrapper>
      <ImageWrapper style={{ left: 0 }}>
        <img src={haiku1} alt="Haiku 1" />
      </ImageWrapper>

      <ContentWrapper>{children}</ContentWrapper>

      <ImageWrapper style={{ right: 0 }}>
        <img src={haiku2} alt="Haiku 2" />
      </ImageWrapper>
    </LayoutWrapper>
  );
}

export default GlobalLayout;
