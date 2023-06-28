import { Box, Button } from "@mui/material";
import React from "react";
import { RightDown } from "./styles";

function RightDownComponents({ items }) {
  return (
    <RightDown.Content>
      {/* NFT 소개 */}
      <Box>
        <RightDown.NFTName>NFT Name #28710</RightDown.NFTName>
        <RightDown.NFTContainer>
          {Object.keys(items).map((item, i) => {
            return i < 2 ? (
              <RightDown.NFTContentsContainer>
                <RightDown.NFTTitle>{item}</RightDown.NFTTitle>
                <RightDown.NFTContent>{items[item]}</RightDown.NFTContent>
              </RightDown.NFTContentsContainer>
            ) : (
              <RightDown.NFTContentsContainer>
                <RightDown.NFTTitle>{item}</RightDown.NFTTitle>
                <RightDown.NFTContent>
                  <RightDown.NFTPrice>{items[item]}</RightDown.NFTPrice>
                  <RightDown.KLAY>KLAY</RightDown.KLAY>
                </RightDown.NFTContent>
              </RightDown.NFTContentsContainer>
            );
          })}
        </RightDown.NFTContainer>
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
    </RightDown.Content>
  );
}

export default RightDownComponents;
