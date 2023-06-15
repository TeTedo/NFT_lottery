import React from "react";
import { Box, Button } from "@mui/material";
import {
  Container,
  LeftUpContent,
  LeftDownContent,
  RightUpContent,
  RightDownContent,
  Contents,
  LeftContents,
  RigthContents,
  NFTImg,
  LeftUpTitle,
  PriceContainer,
  PriceTitles,
} from "./styles";
import ContentsDetail from "../../components/Product/ContentsDetail/ContentsDetail";
import PricesDetail from "../../components/Product/PricesDetail/PricesDetail";

function ProductDetail() {
  return (
    <Container>
      <Contents>
        <LeftContents>
          <LeftUpContent>
            <LeftUpTitle>Details</LeftUpTitle>
            <ContentsDetail />
            <ContentsDetail />
            <ContentsDetail />
          </LeftUpContent>
          <LeftDownContent>
            <PriceContainer>
              <PriceTitles>Price</PriceTitles>
              <PriceTitles>Quantity</PriceTitles>
              <PriceTitles>From</PriceTitles>
            </PriceContainer>
            <PricesDetail />
            <PricesDetail />
            <PricesDetail />
            <PricesDetail />
            <PricesDetail />
            <PricesDetail />
          </LeftDownContent>
        </LeftContents>
        <RigthContents>
          <RightUpContent>
            <NFTImg>ProductDetail3</NFTImg>
          </RightUpContent>
          <RightDownContent>
            {/* NFT 소개 */}
            <Box>
              <Box
                sx={{
                  fontSize: 35,
                  pb: 5,
                  display: "flex",
                  justifyContent: "end",
                }}
              >
                NFT Name #28710
              </Box>
              <Box
                sx={{ width: "auto", display: "flex", justifyContent: "end" }}
              >
                <Box></Box>
                <Box
                  sx={{
                    px: 3,
                    minWidth: 150,
                    textAlign: "center",
                  }}
                >
                  Total
                </Box>
                <Box
                  sx={{
                    px: 3,
                    minWidth: 150,
                    textAlign: "center",
                  }}
                >
                  remain
                </Box>
                <Box
                  sx={{
                    px: 3,
                    minWidth: 150,
                    textAlign: "center",
                  }}
                >
                  Flow price
                </Box>
                <Box
                  sx={{
                    px: 3,
                    minWidth: 150,
                    textAlign: "center",
                  }}
                >
                  Current pirce
                </Box>
              </Box>
              <Box
                sx={{
                  py: 2,
                  color: "#7900e9",
                  width: "auto",
                  display: "flex",
                  justifyContent: "end",
                  pb: 5,
                }}
              >
                <Box
                  sx={{
                    fontSize: 20,
                    px: 3,
                    minWidth: 150,
                    textAlign: "center",
                  }}
                >
                  10
                </Box>
                <Box
                  sx={{
                    fontSize: 20,
                    px: 3,
                    minWidth: 150,
                    textAlign: "center",
                  }}
                >
                  2
                </Box>
                <Box
                  sx={{
                    fontSize: 20,
                    px: 3,
                    minWidth: 150,
                    textAlign: "center",
                    display: "flex",
                    justifyContent: "center",
                    gap: 1,
                  }}
                >
                  <Box>0.001</Box>
                  <Box>KLAY</Box>
                </Box>
                <Box
                  sx={{
                    fontSize: 20,
                    px: 3,
                    minWidth: 150,
                    textAlign: "center",
                    display: "flex",
                    justifyContent: "center",
                    gap: 1,
                  }}
                >
                  <Box>0.001</Box>
                  <Box>KLAY</Box>
                </Box>
              </Box>
            </Box>
            {/* 판매 버튼 */}
            <Box
              sx={{
                background: "#000a06",
                borderRadius: 5,
                alignItems: "center",
                p: 3,
                display: "flex",
                justifyContent: "space-between",
              }}
            >
              <Box sx={{ color: "#49504d", display: "flex" }}>
                판매 종료까지
                <Box sx={{ px: 2, color: "#cdcfce" }}>147일17시간</Box>
                남음
              </Box>
              <Button
                sx={{
                  py: 1.5,
                  background: "#7900e9",
                  fontWeight: 700,
                  fontSize: 20,
                  px: 5,
                }}
                variant="contained"
              >
                BUY
              </Button>
            </Box>
          </RightDownContent>
        </RigthContents>
      </Contents>
    </Container>
  );
}

export default ProductDetail;
