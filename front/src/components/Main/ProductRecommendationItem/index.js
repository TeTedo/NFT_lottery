import { Contents, NFT } from "./styles";

export default function ProductRecommendationItem() {
  return (
    <Contents.Container>
      <Contents.Title>인기 / 추천</Contents.Title>
      <NFT.Container>
        <NFT.Contents>
          <NFT.Title>NFT Name #28716</NFT.Title>
        </NFT.Contents>
        <NFT.Img />
      </NFT.Container>
    </Contents.Container>
  );
}
