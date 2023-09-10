import React from "react";
import { ModalItem } from "./styles";

export default function Modal() {
  return (
    <>
      <ModalItem.Container>
        <ModalItem.TeamName>@2023 Team UNDEFINED</ModalItem.TeamName>
        <ModalItem.Contact.Container>
          <ModalItem.Contact.Title>Contact</ModalItem.Contact.Title>
          <ModalItem.Contact.Description>
            undefind1234@gmail.com
          </ModalItem.Contact.Description>
        </ModalItem.Contact.Container>
      </ModalItem.Container>
    </>
  );
}
