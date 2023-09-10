import React from "react";
import { ModalItem } from "./styles";
import dayjs from "dayjs";
import { DemoContainer } from "@mui/x-date-pickers/internals/demo";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider";
// import { DatePicker } from "@mui/x-date-pickers/DatePicker";
export default function Modal() {
  const [value, setValue] = React.useState(null);
  return (
    <>
      <ModalItem.Container>
        <ModalItem.Box>
          <ModalItem.Title>티켓 구매</ModalItem.Title>

          <ModalItem.TextInput label="티켓 가격" variant="outlined" />
          <ModalItem.TextInput
            label="티켓 수량"
            variant="outlined"
            type="number"
          />
          <LocalizationProvider dateAdapter={AdapterDayjs}>
            <DemoContainer components={["DatePicker"]}>
              <ModalItem.CustomDatePicker
                value={value}
                onChange={(newValue: any) => setValue(newValue)}
              />
            </DemoContainer>
          </LocalizationProvider>
        </ModalItem.Box>
      </ModalItem.Container>
    </>
  );
}
