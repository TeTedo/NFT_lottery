import { useRef } from "react";
import { Contents } from "./styles";
import ProductCard from "../../Common/ProductCard";

export default function ProductClosingItem() {
  const ref = useRef(null);
  return (
    <Contents.Container ref={ref}>
      <ProductCard />
      <ProductCard />
      <ProductCard />
      <ProductCard />
      <ProductCard />
      <ProductCard />
      <ProductCard />
      <ProductCard />
      <ProductCard />
      <ProductCard />
      <ProductCard />
      <ProductCard />
    </Contents.Container>
  );
}
