import React from "react";
import { Container, Contents } from "./styles";
import FinishedProduct from "../../components/Main/ProductClosingItem";
import ProductRecommendationItem from "../../components/Main/ProductRecommendationItem";
// import FinishedProduct from "../../components/Main/FinishedProduct";

function Main() {
  return (
    <Container>
      <Contents.Container>
        <Contents.Title>마감 임박</Contents.Title>
        <FinishedProduct />
        <ProductRecommendationItem />
      </Contents.Container>
    </Container>
  );
}
// test??

export default Main;
