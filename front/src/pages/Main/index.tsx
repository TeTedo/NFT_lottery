import React from "react";
import { Container, Contents } from "./styles";
import ProductClosingItem from "../../components/Main/ProductClosingItem";
import ProductRecommendationItem from "../../components/Main/ProductRecommendationItem";
import ProductList from "../../components/Main/ProductList";
import Footer from "../../components/Common/Footer";

function Main() {
  return (
    <>
    <Container>
      <Contents.Container>
        <Contents.Title>마감 임박</Contents.Title>
        <ProductClosingItem />
        <ProductRecommendationItem />
        <ProductList />
      </Contents.Container>
    </Container>
    <Footer />
    </>
  );
}

export default Main;
