import React from "react";
import { Container, Contents } from "./styles";
import FinishedProduct from "../../components/Main/ProductClosingItem";
import ProductRecommendationItem from "../../components/Main/ProductRecommendationItem";
import ProductList from "../../components/Main/ProductList";
import Footer from "../../components/Common/Footer";
// import FinishedProduct from "../../components/Main/FinishedProduct";

function Main() {
  return (
    <>
    <Container>
      <Contents.Container>
        <Contents.Title>마감 임박</Contents.Title>
        <FinishedProduct />
        <ProductRecommendationItem />
        <ProductList />
      </Contents.Container>
      
    </Container>
    <Footer />
    </>
  );
}

export default Main;
