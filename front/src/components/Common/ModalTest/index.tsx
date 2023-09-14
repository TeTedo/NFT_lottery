import React, { useState } from "react";
import { ModalItem } from "./styles";
// import dayjs from "dayjs";
import { DemoContainer } from "@mui/x-date-pickers/internals/demo";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider";
import { Box } from "@mui/material";
import CloseIcon from "@mui/icons-material/Close";
// import { DatePicker } from "@mui/x-date-pickers/DatePicker";
type ModalProps = {
  isModal: boolean;
  setIsModal?: any;
};
export default function Modal({ isModal, setIsModal }: ModalProps) {
  const [value, setValue] = useState(null);
  const closeModal = () => {
    setIsModal(false);
    setValue(null);
  };
  return (
    <>
      {isModal ? (
        <ModalItem.Container onClick={closeModal}>
          <ModalItem.Box onClick={(e) => e.stopPropagation()}>
            <Box sx={{ position: "absolute", right: "10px", top: "10px" }}>
              <CloseIcon
                sx={{ cursor: "pointer" }}
                onClick={closeModal}
                fontSize="large"
              />
            </Box>
            <ModalItem.Title>티켓 구매</ModalItem.Title>
            <Box
              sx={{
                display: "flex",
                mt: "50px",
                flexDirection: "column",
                gap: "30px",
                // p: "30px",
                width: "400px",
                // border: 1,
              }}
            >
              <Box sx={{ display: "flex", alignItems: "center" }}>
                <Box sx={{ mr: "20px" }}>티켓 가격</Box>
                <ModalItem.TextInput
                  placeholder="티켓 가격"
                  variant="outlined"
                  type="number"
                />
              </Box>
              <Box sx={{ display: "flex", alignItems: "center" }}>
                <Box sx={{ mr: "20px" }}>티켓 수량</Box>
                <ModalItem.TextInput
                  placeholder="티켓 수량"
                  variant="outlined"
                  type="number"
                />
              </Box>
              <LocalizationProvider dateAdapter={AdapterDayjs}>
                <DemoContainer components={["DatePicker"]}>
                  <Box
                    sx={{
                      display: "flex",
                      alignItems: "center",
                      flex: 1,
                    }}
                  >
                    <Box sx={{ mr: "20px" }}>판매 기간</Box>
                    <ModalItem.CustomDatePicker
                      value={value}
                      onChange={(newValue: any) => setValue(newValue)}
                    />
                  </Box>
                </DemoContainer>
              </LocalizationProvider>
              <Box sx={{ display: "flex", justifyContent: "center" }}>
                <ModalItem.Button onClick={closeModal}>
                  구매 하기
                </ModalItem.Button>
              </Box>
            </Box>
          </ModalItem.Box>
        </ModalItem.Container>
      ) : null}
    </>
  );
}
