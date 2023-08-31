// import ProductCard from "components/Common/ProductCard";
import ProductCard from "components/Common/Card/ProductCard";
import { Contents } from "./styles";
import SellProductCard from "components/Common/Card/SellProductCard";
import HoldingProductCard from "components/Common/Card/HoldingProductCard";

export default function ProductList({ category }: any) {
  return (
    <Contents.Container>
      {category === "sell" ? (
        <>
          <SellProductCard />
          <SellProductCard />
          <SellProductCard />
          <SellProductCard />
          <SellProductCard />
          <SellProductCard />
          <SellProductCard />
          <SellProductCard />
          <SellProductCard />
          <SellProductCard />
          <SellProductCard />
          <SellProductCard />
          <SellProductCard />
        </>
      ) : category === "holding" ? (
        <>
          <HoldingProductCard />
          <HoldingProductCard />
          <HoldingProductCard />
          <HoldingProductCard />
          <HoldingProductCard />
          <HoldingProductCard />
          <HoldingProductCard />
          <HoldingProductCard />
          <HoldingProductCard />
          <HoldingProductCard />
          <HoldingProductCard />
          <HoldingProductCard />
          <HoldingProductCard />
        </>
      ) : (
        <>
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
          <ProductCard />
        </>
      )}
    </Contents.Container>
  );
}
