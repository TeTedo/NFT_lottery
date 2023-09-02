import { Box } from "@mui/material";
import { Contents, NFT } from "./styles";
import { useNavigation } from "hooks/useNavigation";

export default function ProductRecommendationItem() {
  const { goToDetail } = useNavigation();
  return (
    <Contents.Container>
      <Contents.Title>인기 / 추천</Contents.Title>
      <NFT.Container>
        <NFT.Contents>
          <NFT.Title>NFT Name #28716</NFT.Title>
          <NFT.Data.Container>
            <NFT.Data.Content>
              <NFT.Data.Title>Total</NFT.Data.Title>
              <NFT.Data.Description>10</NFT.Data.Description>
            </NFT.Data.Content>
            <NFT.Data.Content>
              <NFT.Data.Title>remain</NFT.Data.Title>
              <NFT.Data.Description>2</NFT.Data.Description>
            </NFT.Data.Content>
            <NFT.Data.Content>
              <NFT.Data.Title>Flow price</NFT.Data.Title>
              <NFT.Data.Description>0.001</NFT.Data.Description>
            </NFT.Data.Content>
            <NFT.Data.Content>
              <NFT.Data.Title>Current price</NFT.Data.Title>
              <NFT.Data.Description>3.5</NFT.Data.Description>
            </NFT.Data.Content>
          </NFT.Data.Container>
          <NFT.Box.Container>
            <Box sx={{ display: "flex", gap: "10px" }}>
              <NFT.Box.Text>판매종료까지</NFT.Box.Text>
              <NFT.Box.Time>147일 17시간</NFT.Box.Time>
              <NFT.Box.Text>남음</NFT.Box.Text>
            </Box>
            <NFT.Box.Button onClick={() => goToDetail("1")}>BUY</NFT.Box.Button>
          </NFT.Box.Container>
        </NFT.Contents>
        <NFT.Img />
      </NFT.Container>
    </Contents.Container>
  );
}
