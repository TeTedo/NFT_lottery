/* Autogenerated file. Do not edit manually. */
/* tslint:disable */
/* eslint-disable */
import type {
  BaseContract,
  BigNumber,
  BigNumberish,
  BytesLike,
  CallOverrides,
  PopulatedTransaction,
  Signer,
  utils,
} from "ethers";
import type { FunctionFragment, Result } from "@ethersproject/abi";
import type { Listener, Provider } from "@ethersproject/providers";
import type {
  TypedEventFilter,
  TypedEvent,
  TypedListener,
  OnEvent,
  PromiseOrValue,
} from "../common";

export declare namespace UndefinedUtil {
  export type BalanceOfNftStruct = {
    nftCa: PromiseOrValue<string>;
    balance: PromiseOrValue<BigNumberish>;
  };

  export type BalanceOfNftStructOutput = [string, BigNumber] & {
    nftCa: string;
    balance: BigNumber;
  };

  export type TokensOfOwnerWithUriStruct = {
    tokenCa: PromiseOrValue<string>;
    tokenIds: PromiseOrValue<BigNumberish>[];
    tokenUris: PromiseOrValue<string>[];
  };

  export type TokensOfOwnerWithUriStructOutput = [
    string,
    BigNumber[],
    string[]
  ] & { tokenCa: string; tokenIds: BigNumber[]; tokenUris: string[] };

  export type TokenOfOwnerStruct = {
    tokenCa: PromiseOrValue<string>;
    tokenId: PromiseOrValue<BigNumberish>;
  };

  export type TokenOfOwnerStructOutput = [string, BigNumber] & {
    tokenCa: string;
    tokenId: BigNumber;
  };
}

export interface UndefinedUtilInterface extends utils.Interface {
  functions: {
    "getBalanceOfListedNft(address[],address)": FunctionFragment;
    "getName(address)": FunctionFragment;
    "getOwnerOf(address,uint256)": FunctionFragment;
    "getSymbol(address)": FunctionFragment;
    "getTokenUri(address,uint256)": FunctionFragment;
    "getTokenUriBatch(address,uint256[])": FunctionFragment;
    "getTokensOfOwner(address,address)": FunctionFragment;
    "getTokensOfOwnerBatch((address,uint256)[],address)": FunctionFragment;
    "getTokensOfOwnerWithListedNft(address[],address)": FunctionFragment;
  };

  getFunction(
    nameOrSignatureOrTopic:
      | "getBalanceOfListedNft"
      | "getName"
      | "getOwnerOf"
      | "getSymbol"
      | "getTokenUri"
      | "getTokenUriBatch"
      | "getTokensOfOwner"
      | "getTokensOfOwnerBatch"
      | "getTokensOfOwnerWithListedNft"
  ): FunctionFragment;

  encodeFunctionData(
    functionFragment: "getBalanceOfListedNft",
    values: [PromiseOrValue<string>[], PromiseOrValue<string>]
  ): string;
  encodeFunctionData(
    functionFragment: "getName",
    values: [PromiseOrValue<string>]
  ): string;
  encodeFunctionData(
    functionFragment: "getOwnerOf",
    values: [PromiseOrValue<string>, PromiseOrValue<BigNumberish>]
  ): string;
  encodeFunctionData(
    functionFragment: "getSymbol",
    values: [PromiseOrValue<string>]
  ): string;
  encodeFunctionData(
    functionFragment: "getTokenUri",
    values: [PromiseOrValue<string>, PromiseOrValue<BigNumberish>]
  ): string;
  encodeFunctionData(
    functionFragment: "getTokenUriBatch",
    values: [PromiseOrValue<string>, PromiseOrValue<BigNumberish>[]]
  ): string;
  encodeFunctionData(
    functionFragment: "getTokensOfOwner",
    values: [PromiseOrValue<string>, PromiseOrValue<string>]
  ): string;
  encodeFunctionData(
    functionFragment: "getTokensOfOwnerBatch",
    values: [UndefinedUtil.BalanceOfNftStruct[], PromiseOrValue<string>]
  ): string;
  encodeFunctionData(
    functionFragment: "getTokensOfOwnerWithListedNft",
    values: [PromiseOrValue<string>[], PromiseOrValue<string>]
  ): string;

  decodeFunctionResult(
    functionFragment: "getBalanceOfListedNft",
    data: BytesLike
  ): Result;
  decodeFunctionResult(functionFragment: "getName", data: BytesLike): Result;
  decodeFunctionResult(functionFragment: "getOwnerOf", data: BytesLike): Result;
  decodeFunctionResult(functionFragment: "getSymbol", data: BytesLike): Result;
  decodeFunctionResult(
    functionFragment: "getTokenUri",
    data: BytesLike
  ): Result;
  decodeFunctionResult(
    functionFragment: "getTokenUriBatch",
    data: BytesLike
  ): Result;
  decodeFunctionResult(
    functionFragment: "getTokensOfOwner",
    data: BytesLike
  ): Result;
  decodeFunctionResult(
    functionFragment: "getTokensOfOwnerBatch",
    data: BytesLike
  ): Result;
  decodeFunctionResult(
    functionFragment: "getTokensOfOwnerWithListedNft",
    data: BytesLike
  ): Result;

  events: {};
}

export interface UndefinedUtil extends BaseContract {
  connect(signerOrProvider: Signer | Provider | string): this;
  attach(addressOrName: string): this;
  deployed(): Promise<this>;

  interface: UndefinedUtilInterface;

  queryFilter<TEvent extends TypedEvent>(
    event: TypedEventFilter<TEvent>,
    fromBlockOrBlockhash?: string | number | undefined,
    toBlock?: string | number | undefined
  ): Promise<Array<TEvent>>;

  listeners<TEvent extends TypedEvent>(
    eventFilter?: TypedEventFilter<TEvent>
  ): Array<TypedListener<TEvent>>;
  listeners(eventName?: string): Array<Listener>;
  removeAllListeners<TEvent extends TypedEvent>(
    eventFilter: TypedEventFilter<TEvent>
  ): this;
  removeAllListeners(eventName?: string): this;
  off: OnEvent<this>;
  on: OnEvent<this>;
  once: OnEvent<this>;
  removeListener: OnEvent<this>;

  functions: {
    getBalanceOfListedNft(
      listedNftList: PromiseOrValue<string>[],
      owner: PromiseOrValue<string>,
      overrides?: CallOverrides
    ): Promise<[UndefinedUtil.BalanceOfNftStructOutput[]]>;

    getName(
      tokenCa: PromiseOrValue<string>,
      overrides?: CallOverrides
    ): Promise<[string]>;

    getOwnerOf(
      nftCa: PromiseOrValue<string>,
      tokenId: PromiseOrValue<BigNumberish>,
      overrides?: CallOverrides
    ): Promise<[string]>;

    getSymbol(
      tokenCa: PromiseOrValue<string>,
      overrides?: CallOverrides
    ): Promise<[string]>;

    getTokenUri(
      tokenCa: PromiseOrValue<string>,
      tokenId: PromiseOrValue<BigNumberish>,
      overrides?: CallOverrides
    ): Promise<[string]>;

    getTokenUriBatch(
      tokenCa: PromiseOrValue<string>,
      tokenIds: PromiseOrValue<BigNumberish>[],
      overrides?: CallOverrides
    ): Promise<[UndefinedUtil.TokensOfOwnerWithUriStructOutput]>;

    getTokensOfOwner(
      tokenCa: PromiseOrValue<string>,
      owner: PromiseOrValue<string>,
      overrides?: CallOverrides
    ): Promise<[BigNumber[]]>;

    getTokensOfOwnerBatch(
      balanceOfNftList: UndefinedUtil.BalanceOfNftStruct[],
      owner: PromiseOrValue<string>,
      overrides?: CallOverrides
    ): Promise<[UndefinedUtil.TokenOfOwnerStructOutput[]]>;

    getTokensOfOwnerWithListedNft(
      listedNftList: PromiseOrValue<string>[],
      owner: PromiseOrValue<string>,
      overrides?: CallOverrides
    ): Promise<
      [UndefinedUtil.TokenOfOwnerStructOutput[]] & {
        tokensOfOwner: UndefinedUtil.TokenOfOwnerStructOutput[];
      }
    >;
  };

  getBalanceOfListedNft(
    listedNftList: PromiseOrValue<string>[],
    owner: PromiseOrValue<string>,
    overrides?: CallOverrides
  ): Promise<UndefinedUtil.BalanceOfNftStructOutput[]>;

  getName(
    tokenCa: PromiseOrValue<string>,
    overrides?: CallOverrides
  ): Promise<string>;

  getOwnerOf(
    nftCa: PromiseOrValue<string>,
    tokenId: PromiseOrValue<BigNumberish>,
    overrides?: CallOverrides
  ): Promise<string>;

  getSymbol(
    tokenCa: PromiseOrValue<string>,
    overrides?: CallOverrides
  ): Promise<string>;

  getTokenUri(
    tokenCa: PromiseOrValue<string>,
    tokenId: PromiseOrValue<BigNumberish>,
    overrides?: CallOverrides
  ): Promise<string>;

  getTokenUriBatch(
    tokenCa: PromiseOrValue<string>,
    tokenIds: PromiseOrValue<BigNumberish>[],
    overrides?: CallOverrides
  ): Promise<UndefinedUtil.TokensOfOwnerWithUriStructOutput>;

  getTokensOfOwner(
    tokenCa: PromiseOrValue<string>,
    owner: PromiseOrValue<string>,
    overrides?: CallOverrides
  ): Promise<BigNumber[]>;

  getTokensOfOwnerBatch(
    balanceOfNftList: UndefinedUtil.BalanceOfNftStruct[],
    owner: PromiseOrValue<string>,
    overrides?: CallOverrides
  ): Promise<UndefinedUtil.TokenOfOwnerStructOutput[]>;

  getTokensOfOwnerWithListedNft(
    listedNftList: PromiseOrValue<string>[],
    owner: PromiseOrValue<string>,
    overrides?: CallOverrides
  ): Promise<UndefinedUtil.TokenOfOwnerStructOutput[]>;

  callStatic: {
    getBalanceOfListedNft(
      listedNftList: PromiseOrValue<string>[],
      owner: PromiseOrValue<string>,
      overrides?: CallOverrides
    ): Promise<UndefinedUtil.BalanceOfNftStructOutput[]>;

    getName(
      tokenCa: PromiseOrValue<string>,
      overrides?: CallOverrides
    ): Promise<string>;

    getOwnerOf(
      nftCa: PromiseOrValue<string>,
      tokenId: PromiseOrValue<BigNumberish>,
      overrides?: CallOverrides
    ): Promise<string>;

    getSymbol(
      tokenCa: PromiseOrValue<string>,
      overrides?: CallOverrides
    ): Promise<string>;

    getTokenUri(
      tokenCa: PromiseOrValue<string>,
      tokenId: PromiseOrValue<BigNumberish>,
      overrides?: CallOverrides
    ): Promise<string>;

    getTokenUriBatch(
      tokenCa: PromiseOrValue<string>,
      tokenIds: PromiseOrValue<BigNumberish>[],
      overrides?: CallOverrides
    ): Promise<UndefinedUtil.TokensOfOwnerWithUriStructOutput>;

    getTokensOfOwner(
      tokenCa: PromiseOrValue<string>,
      owner: PromiseOrValue<string>,
      overrides?: CallOverrides
    ): Promise<BigNumber[]>;

    getTokensOfOwnerBatch(
      balanceOfNftList: UndefinedUtil.BalanceOfNftStruct[],
      owner: PromiseOrValue<string>,
      overrides?: CallOverrides
    ): Promise<UndefinedUtil.TokenOfOwnerStructOutput[]>;

    getTokensOfOwnerWithListedNft(
      listedNftList: PromiseOrValue<string>[],
      owner: PromiseOrValue<string>,
      overrides?: CallOverrides
    ): Promise<UndefinedUtil.TokenOfOwnerStructOutput[]>;
  };

  filters: {};

  estimateGas: {
    getBalanceOfListedNft(
      listedNftList: PromiseOrValue<string>[],
      owner: PromiseOrValue<string>,
      overrides?: CallOverrides
    ): Promise<BigNumber>;

    getName(
      tokenCa: PromiseOrValue<string>,
      overrides?: CallOverrides
    ): Promise<BigNumber>;

    getOwnerOf(
      nftCa: PromiseOrValue<string>,
      tokenId: PromiseOrValue<BigNumberish>,
      overrides?: CallOverrides
    ): Promise<BigNumber>;

    getSymbol(
      tokenCa: PromiseOrValue<string>,
      overrides?: CallOverrides
    ): Promise<BigNumber>;

    getTokenUri(
      tokenCa: PromiseOrValue<string>,
      tokenId: PromiseOrValue<BigNumberish>,
      overrides?: CallOverrides
    ): Promise<BigNumber>;

    getTokenUriBatch(
      tokenCa: PromiseOrValue<string>,
      tokenIds: PromiseOrValue<BigNumberish>[],
      overrides?: CallOverrides
    ): Promise<BigNumber>;

    getTokensOfOwner(
      tokenCa: PromiseOrValue<string>,
      owner: PromiseOrValue<string>,
      overrides?: CallOverrides
    ): Promise<BigNumber>;

    getTokensOfOwnerBatch(
      balanceOfNftList: UndefinedUtil.BalanceOfNftStruct[],
      owner: PromiseOrValue<string>,
      overrides?: CallOverrides
    ): Promise<BigNumber>;

    getTokensOfOwnerWithListedNft(
      listedNftList: PromiseOrValue<string>[],
      owner: PromiseOrValue<string>,
      overrides?: CallOverrides
    ): Promise<BigNumber>;
  };

  populateTransaction: {
    getBalanceOfListedNft(
      listedNftList: PromiseOrValue<string>[],
      owner: PromiseOrValue<string>,
      overrides?: CallOverrides
    ): Promise<PopulatedTransaction>;

    getName(
      tokenCa: PromiseOrValue<string>,
      overrides?: CallOverrides
    ): Promise<PopulatedTransaction>;

    getOwnerOf(
      nftCa: PromiseOrValue<string>,
      tokenId: PromiseOrValue<BigNumberish>,
      overrides?: CallOverrides
    ): Promise<PopulatedTransaction>;

    getSymbol(
      tokenCa: PromiseOrValue<string>,
      overrides?: CallOverrides
    ): Promise<PopulatedTransaction>;

    getTokenUri(
      tokenCa: PromiseOrValue<string>,
      tokenId: PromiseOrValue<BigNumberish>,
      overrides?: CallOverrides
    ): Promise<PopulatedTransaction>;

    getTokenUriBatch(
      tokenCa: PromiseOrValue<string>,
      tokenIds: PromiseOrValue<BigNumberish>[],
      overrides?: CallOverrides
    ): Promise<PopulatedTransaction>;

    getTokensOfOwner(
      tokenCa: PromiseOrValue<string>,
      owner: PromiseOrValue<string>,
      overrides?: CallOverrides
    ): Promise<PopulatedTransaction>;

    getTokensOfOwnerBatch(
      balanceOfNftList: UndefinedUtil.BalanceOfNftStruct[],
      owner: PromiseOrValue<string>,
      overrides?: CallOverrides
    ): Promise<PopulatedTransaction>;

    getTokensOfOwnerWithListedNft(
      listedNftList: PromiseOrValue<string>[],
      owner: PromiseOrValue<string>,
      overrides?: CallOverrides
    ): Promise<PopulatedTransaction>;
  };
}
