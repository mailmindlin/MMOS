package com.mindlin.spc;

import java.util.ArrayList;
import java.util.List;

public interface SPCComp {
	TDataDefs fDD;
	TDataspaceEntry fCurrentStruct;
	TMapList fNamedTypes;
	ArrayList<String> fEmittedLocals;
	List<Variable> fLocals;
	List<Variable> fParams;
	List<Variable> fGlobals;
	TFunctionParameters fFuncParams;
	TInlineFunction fCurrentInlineFunction;
	TInlineFunctions fInlineFunctions;
	TArrayHelperVars fArrayHelpers;
	List<String> fASM;
	List<String> fMessages;
	TMemoryStream fMS;
	char fTempChar;
	boolean fCCSet;
	List<String> fIncludeDirs;
	String fCurFile;
	String fOldCurFile;
	TOnCompilerMessage fOnCompMSg;
	String fDirLine;
	String fCurrentLine;
	String fExpStr;
	boolean fExpStrHasVars;
	String fBoolSubExpStr;
	boolean fBoolSubExpStrHasVars;
	ArrayList<String> fAPIFunctions;
	ArrayList<String> fAPIStrFunctions;
	ArrayList<String> fThreadNames;
	String fCurrentThreadName;
	int fBytesRead;
	ArrayList<String> fSwitchFixups;
	ArrayList<String> fSwitchRegNames;
	int fSwitchDepth;
	TNBCExpParser fCalc;
	int fOptimizeLevel;
	int fInlineDepth;
	List<List<String>> fInlineStack; // list of List<String>
	int fNestingLevel;
	char fLHSDataType;
	String fLHSName;
	boolean fWarningsOff;
	ArrayList<String> fFunctionNameCallStack;
	boolean fSemiColonRequired;
	boolean fExpressionIsSigned;
	List<String> fArrayIndexStack;
	String fUDTOnStack;
	boolean fLastExpressionOptimizedToConst;
	String fLastLoadedConst;
	boolean fProcessingMathAssignment;
	boolean fProcessingAsmBlock;
	boolean fNoCommaOperator;
	boolean fDerefAssignment;
	boolean fDerefValue;
	boolean fAddressOfValue;
	boolean fAutoStart;
	TProcessObject fIGDProcess;

	public void AddArrayDataDefinition(String aname, char dt, char lenexpr, String tname);

	public boolean AmInlining();

	public void IncrementInlineDepth();

	public void DecrementInlineDepth();

	public void HandleSpecialNames();

	public void DecrementNestingLevel();

	public void GetCharX();

	public void GetChar();

	public void Init();

	public abstract void Prog();// virtual

	public void SkipCommentBlock();

	public void SkipLine();

	public void SkipDirectiveLine();

	public void SkipWhite();

	public void GetDirective();

	public void GetName();

	public void GetNum();

	public void GetHexNum();

	public void GetCharLit();

	public void GetOp();

	public void Next(boolean bProcessDirectives);

	public void MatchString(String x);

	public void Semi();

	public void NotNumericFactor();

	public void NumericFactor();

	public void Modulo();

	public void Divide();

	public void Multiply();

	public void Term();

	public void Add();

	public void Expression();

	public void DoPreIncOrDec(boolean bPutOnStack);

	public boolean IncrementOrDecrement();

	public String OptimizeExpression(String str, final int idx, boolean bFlag, final String aValue);

	public void Subtract();

	public void CommaExpression();

	public void BoolExpression();

	public void Relation();

	public void StoreZeroFlag();

	public void CallRoutine(final String name);

	public void ReturnFromRoutine();

	public boolean ValueIsArrayType();

	public boolean ValueIsUserDefinedType();

	public void BoolTerm();

	public void BitOr();

	public void BitXor();

	public void BitAnd();

	public boolean[] TypesAreCompatible(char lhs, char rhs);

	public String GetParamName(String[] procname, int idx);

	public void DoCall(String procname);

	public String GetValueOf(final String name);

	public void DoCallAPIFunc(String procname);

	public int APIFuncNameToID(String procname);

	public boolean IsAPIFunc(String procname);

	public void DoAssignValue(final String aName, char[] dt, boolean bNoChecks);

	public void DoLocalArrayInit(final String[] aName, final String[] ival, char dt);

	public void DoArrayAssignValue(final String[] aName, String[] idx, char dt);

	public boolean DoNewArrayIndex(char theArrayDT, String theArray, String aLHSName);

	public void OffsetUDTPointer(final String UDTType, final String aPointer);

	public void OffsetArrayPointer(final String[] ArrayType, final String aPointer);

	public void Assignment();

	public void CheckNotfinalant(final String aName);

	public String Checkfinalant(final String aName);

	public boolean Block(final String[] lend, final String lstart);

	public void BlockStatements(final String[] lend, final String lstart);

	public void CheckBytesRead(final int oldBytesRead);

	public void DoFor();

	public void DoIf(final String lend, String lstart);

	public void DoWhile();

	public void DoDoWhile();

	public void DoRepeat();

	public void DoAsm(char dt);

	public String DecorateVariables(final String asmStr);

	public void DoSwitch(final String lstart);

	public void DoSwitchCase();

	public String GetCaseConstant();

	public void DoSwitchDefault();

	public int SwitchFixupIndex();

	public String SwitchRegisterName();

	public void ClearSwitchFixups();

	public void FixupSwitch(int idx, String lbl);

	public void DoLabel();

	public void DoStart();

	public void CommaStatement(final String lend, String lstart);

	public void Statement(final String lend, String lstart);

	public void ProcessDirectives(boolean bScan);

	public void HandlePoundLine();

	public void HandlePoundPragma();

	public void HandlePoundReset();

	public char ArrayOfType(char dt, int dimensions);

	public char GetVariableType(char vt);

	public void CheckForValidDataType(char dt);

	public char RemoveArrayDimension(char dt);

	public char AddArrayDimension(char dt);

	public void IncLineNumber();

	public int AddLocal(String name, char dt, final String tname, boolean bfinal, final String lenexp,
			boolean bPointer);

	public void AllocGlobal(final String tname, char dt, boolean bInline, boolean bfinal, boolean bStatic);

	public void AllocLocal(final String sub, String tname, char dt, boolean bfinal, boolean bStatic);

	public String GetInitialValue(char dt);

	public void DoLocals(final String sub);

	public void AddFunctionParameter(String pname, String varname, String tname, int idx, char ptype,
			boolean bIsfinal, boolean bIsRef, boolean bIsArray, int aDim, boolean bHasDefault,
			String defValue);

	public int FormalList(boolean protoexists, String procname);

	public void ProcedureBlock();

	public void InitializeGlobalArrays();

	public void EmitGlobalDataInitSubroutine();

	public void FunctionBlock(String Name, String tname, char dt, boolean bInline, boolean bPointer);

	public void AbortMsg(final String s);

	public void WarningMsg(final String s);

	public void Expected(final String s);

	public void Undefined(final String n);

	public void Duplicate(final String n);

	public void CheckIdent();

	public void CheckEnhancedFirmware();

	public void CheckDataType(char dt);

	public void CheckTypeCompatibility(TFunctionParameter fp, char dt, final String name);

	// public int SizeOfType(char dt);
	public int AddEntry(String N, char dt, final String tname, String lenexp, boolean bfinal,
			boolean bPointer);

	public void CheckDup(String N);

	public void CheckTable(final String N);

	public void CheckGlobal(final String N);

	public void AddParam(String N, char dt, final String tname, boolean bfinal, boolean bHasDefault,
			boolean bIsReference, final String defValue);

	public char DataType(final String n);

	public String DataTypeName(final String n);

	public void CheckAndLoadVar(final String Name);

	public void LoadVar(final String Name);

	public void LoadVarToDest(final String Dest, final String Name);

	public void CheckNotProc(final String Name);

	public void CheckAndStore(final String Name);

	public void Store(final String name);

	public void CopyVar(final String dest, String src);

	public void Allocate(final String Name, String aVal, String Val, String tname, char dt, int cnt);

	public void InitializeArray(final String Name, String aVal, String Val, String tname, char dt,
			String lenexpr);

	// public String InlineDecoration;
	public void Epilog(boolean bIsSub);

	public void Prolog(final String name, boolean bIsSub);

	public void EmitRegisters();

	public void EmitStackVariables();

	public void EmitInlineParametersAndLocals(TInlineFunction func);

	public void EmitLn(final String s);

	public void EmitLnNoTab(final String s);

	public void PostLabel(final String L);

	public void LoadfinalToDest(final String dest, String n);

	public void Loadfinal(final String n);

	public void Negate();

	public void NotIt(final String aName);

	public void Complement();

	public void PopAdd();

	public void PopAnd();

	public void PopCmpEqual();

	public void PopCmpGreater();

	public void PopCmpGreaterOrEqual();

	public void PopCmpLess();

	public void PopCmpLessOrEqual();

	public void PopCmpNEqual();

	public void PopMod();

	public void PopDiv();

	public void PopLeftShift();

	public void PopMul();

	public void PopOr();

	public void PopRightShift();

	public void PopSub();

	public void PopXor();

	public void PushPrim();

	public void Branch(final String L);

	public void BranchFalse(final String L);

	public void BranchTrue(final String L);

	public void BranchPositive(final String L);

	public void ClearReg();

	public void ArrayAssignment(final String name, char dt, boolean bIndexed);

	public void UDTAssignment(final String name);

	public void GetAndStoreUDT(final String name);

	public void MathAssignment(final String name);

	public void DoAdd(final String dest, String src);

	public void DoAddImmediate(final String dest, final int offset);

	public void StoreAdd(final String name);

	public void StoreDiv(final String name);

	public void StoreMod(final String name);

	public void StoreAnd(final String name);

	public void StoreOr(final String name);

	public void StoreXor(final String name);

	public void StoreShift(boolean bRight, final String name);

	public void StoreMul(final String name);

	public void StoreSub(final String name);

	public void StoreInc(final String name);

	public void StoreDec(final String name);

	public void DoAPICommands(final String lend, String lstart);

	public void DoBreakContinue(int idx, final String lbl);

	public void DoExitTo();

	public void DoOpenLog();

	public void DoCloseLog();

	public void DoWriteToLog();

	public void DoReadFromLog();

	public void DoLogStatus();

	public void DoRotate(final int idx);

	public void DoPush();

	public void DoPop();

	public void DoSquareRoot();

	public void DoRunProgram();

	public void DoWait();

	public void DoStopProcesses();

	public void DoPutChar();

	public void DoPutString();

	public void DoPrintf();

	public void DoStop();

	public void DoGoto();

	public void DoReturn();

	public void DoAbs();

	public void DoSign();

	public void DoSizeOf();

	public void ReportProblem(final int lineNo, final String fName, String msg, final boolean err);

	public void Scan();

	public boolean IsWhite(char c);

	public boolean IsRelop(char c);

	public boolean IsOrop(char c);

	public boolean IsDigit(char c);

	public boolean IsHex(char c);

	public boolean IsAlNum(char c);

	public boolean IsAddop(char c);

	public boolean IsMulop(char c);

	public void CheckNumeric();

	public default boolean ValueIsNumeric() {
		String vName;
		int idx;
		Variable V;
		boolean result=true;
		
	}

	public void LoadAPIFunctions();

	public void AddAPIFunction(final String name, int id);

	public TSymbolType WhatIs(final String n);

	public String TempSignedLongName();

	public String RegisterName(String name);

	// public String ZeroFlag;
	public String tos();

	public String ReplaceTokens(final String line);

	public void EmitAsmLines(final String s);

	public void EmitPoundLine();

	public boolean IsLocal(String n);

	public int LocalIdx(String n);

	public boolean IsOldParam(String n);

	public boolean IsFuncParam(String n, boolean bStripInline);

	public boolean IsParam(String n);

	public int ParamIdx(String n);

	public int AllocateHelper(String aName, String tname, char dt, int cnt);

	public String GetDecoratedValue();

	public String GetDecoratedIdent(final String val);

	public void PopCmpHelper(final TCompareCode cc);

	public void CmpHelper(final TCompareCode cc, final String lhs, String rhs);

	public void BoolSubExpression();

	public String NewLabel();

	public void StoreArray(final String name, String idx, String val);

	public void CopyArray(final String name, String val);

	public void DoIndex(final String aValue, String aName, String aIndex);

	public void CheckTask(final String Name);

	public void NumericRelation();

	public void NumericRelationLTGT();

	public void NumericShiftLeftRight();

	public String[] GetASMSrc();

	public char FunctionReturnType(final String name);

	public int FunctionParameterCount(final String name);

	public int FunctionRequiredParameterCount(final String name);

	public char FunctionParameterType(final String name, int idx);

	public void ClearLocals();

	public void ClearParams();

	public void ClearGlobals();

	public boolean IsGlobal(String n);

	public int GlobalIdx(String n);

	public void SetDefines(final String[] Value);

	public TFunctionParameter GetFunctionParam(final String procname, int idx);

	public String AdvanceToNextParam();

	public boolean[] FunctionParameterIsfinalant(final String name, int idx);

	public boolean FunctionParameterIsReference(final String name, int idx);

	public String[] FunctionParameterDefaultValue(final String name, int idx);

	public boolean[] FunctionParameterHasDefault(final String name, int idx);

	public boolean IsPointer(final String aName);

	public boolean IsParamfinal(String n);

	public boolean IsParamPointer(String n);

	public boolean IsLocalfinal(String n);

	public boolean IsLocalPointer(String n);

	public boolean IsGlobalfinal(String n);

	public boolean IsGlobalPointer(String n);

	public String GetUDTType(String n);

	public void AddTypeNameAlias(final String lbl, String args);

	public String TranslateTypeName(final String name);

	public void ProcessEnum(boolean bGlobal);

	public void ProcessTypedef();

	public void ProcessStruct(boolean bTypeDef);

	public void CheckForTypedef(boolean bfinal, boolean bStatic, boolean bInline);

	public boolean IsUserDefinedType(final String name);

	public char DataTypeOfDataspaceEntry(TDataspaceEntry DE);

	public void LoadSourceStream(TStream Src, TStream Dest);

	public void CheckForMain();

	public String ProcessArrayDimensions(String lenexpr);

	public void CheckForCast();

	public void HandleCast();
}
